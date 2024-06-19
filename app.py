from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
import pandas as pd
import os
import tensorflow as tf
import googlemaps
import requests
import mysql.connector
from dotenv import load_dotenv

load_dotenv()

model_url = os.getenv("MODEL_URL")

response = requests.get(model_url)
with open("temp_model.tflite", "wb") as f:
    f.write(response.content)

app = Flask(__name__)
CORS(app)

app.config["DEBUG"] = os.environ.get("FLASK_DEBUG")

DB_CONFIG = {
    "user": os.getenv("DB_USER"),
    "password": os.getenv("DB_PASSWORD"),
    "host": os.getenv("DB_HOST"),
    "database": os.getenv("DB_DATABASE"),
}


def get_db_connection():
    """Membuat koneksi ke database MySQL."""
    conn = mysql.connector.connect(**DB_CONFIG)
    return conn


def get_data_from_db():
    """Mengambil data restoran dari database."""
    conn = get_db_connection()
    query = "SELECT * FROM mytable"
    df = pd.read_sql(query, conn)
    df["id"] = df["id"].astype(int)
    conn.close()
    return df


# Services
def get_distance_with_google_maps(latitude1, longitude1, latitude2, longitude2):
    """Menggunakan Google Maps Distance Matrix API untuk menghitung jarak antara dua titik."""
    gmaps = googlemaps.Client(key=os.getenv("GMAPS_API"))
    origin = f"{latitude1},{longitude1}"
    destination = f"{latitude2},{longitude2}"
    distance_matrix = gmaps.distance_matrix(origin, destination)
    
    if distance_matrix["rows"][0]["elements"][0]["status"] == "ZERO_RESULTS":
        return None
        
    distance = distance_matrix["rows"][0]["elements"][0]["distance"]["value"] / 1000
    return distance


def get_food_recommendations(
    latitude, longitude, restaurants_data, model_path, k=10, max_distance=20
):
    """Mengembalikan rekomendasi makanan beserta jaraknya dari latitude dan longitude."""
    interpreter = tf.lite.Interpreter(model_path=model_path)
    interpreter.allocate_tensors()
    input_details = interpreter.get_input_details()
    output_details = interpreter.get_output_details()
    user_location = np.array([[latitude, longitude]], dtype=np.float32)
    interpreter.set_tensor(input_details[0]["index"], user_location)
    interpreter.invoke()
    output_data = interpreter.get_tensor(output_details[0]["index"])
    recommended_restaurants_indices = np.argsort(output_data[0])[:k]
    recommended_restaurants = []

    for idx in recommended_restaurants_indices:
        restaurant_id = restaurants_data.iloc[idx]["id"]
        restaurant_name = restaurants_data.iloc[idx]["nama"]
        restaurant_lat = float(restaurants_data.iloc[idx]["lat"])
        restaurant_lon = float(restaurants_data.iloc[idx]["lon"])
        distance = get_distance_with_google_maps(
            latitude, longitude, restaurant_lat, restaurant_lon
        )
        if distance is not None and distance <= max_distance:
            restaurant_info = {
                "id": int(restaurant_id),
                "nama": restaurant_name,
                "description": restaurants_data.iloc[idx]["description"],
                "photoUrl": restaurants_data.iloc[idx]["photoUrl"],
                "estimatePrice": restaurants_data.iloc[idx]["estimatePrice"],
                "lat": restaurant_lat,
                "lon": restaurant_lon,
                "distance": distance,
            }
            recommended_restaurants.append(restaurant_info)

    recommended_restaurants = sorted(
        recommended_restaurants, key=lambda x: x["distance"]
    )[:10]
    return recommended_restaurants


def search_restaurants_by_name(
    names, restaurants_data, latitude=None, longitude=None, limit=10, max_distance=20
):
    """Mencari restoran berdasarkan nama dan mengembalikan hasilnya beserta jaraknya jika lokasi diberikan."""
    # Initialize an empty list to store results
    results = []

    for name in names:
        filtered_restaurants = restaurants_data[
            restaurants_data["nama"].str.contains(name, case=False, na=False)
        ]
        if latitude is not None and longitude is not None:
            for idx, row in filtered_restaurants.iterrows():
                restaurant_lat = row["lat"]
                restaurant_lon = row["lon"]
                distance = get_distance_with_google_maps(
                    latitude, longitude, restaurant_lat, restaurant_lon
                )
                if distance <= max_distance:
                    results.append(
                        {
                            "id": row["id"],
                            "nama": row["nama"],
                            "description": row["description"],
                            "photoUrl": row["photoUrl"],
                            "estimatePrice": row["estimatePrice"],
                            "latitude": restaurant_lat,
                            "longitude": restaurant_lon,
                            "distance": distance,
                        }
                    )
        else:
            results.extend(
                filtered_restaurants[
                    ["id", "nama", "description", "photoUrl", "estimatePrice", "lat", "lon"]
                ].to_dict(orient="records")
            )

    # Sort the results by distance if coordinates are provided
    if latitude is not None and longitude is not None:
        results = sorted(results, key=lambda x: x["distance"])[:limit]
    else:
        results = results[:limit]

    return results


def get_restaurant_by_id(id, restaurants_data):
    """Mendapatkan data restoran berdasarkan id."""
    restaurant = restaurants_data[restaurants_data["id"] == id].to_dict(
        orient="records"
    )
    if restaurant:
        return restaurant[0]
    else:
        return None


# Routing
@app.route("/", methods=["GET"])
def index():
    """Route untuk endpoint utama."""
    return jsonify({"message": "Kukuliner Endpoint API using Flask"})


@app.route("/api/culinary", methods=["GET"])
def get_all_restaurants():
    """Mendapatkan semua data restoran dari database."""
    restaurants_data = get_data_from_db()

    # Mengubah DataFrame menjadi list of dictionaries
    restaurants_list = restaurants_data.to_dict(orient="records")

    return jsonify({"listKuliner": restaurants_list})


@app.route("/api/culinary/search", methods=["GET"])
def search_restaurant():
    names = request.args.getlist("name")  # Accepting multiple names as a list

    latitude = request.args.get("lat", type=float, default=None)
    longitude = request.args.get("lon", type=float, default=None)

    if not names:
        return jsonify({"error": "Name parameter is required"}), 400

    # Data restoran dari database
    restaurants_data = get_data_from_db()

    search_results = search_restaurants_by_name(
        names, restaurants_data, latitude, longitude
    )
    return jsonify({"listKuliner": search_results})


@app.route("/api/culinary/<int:id>", methods=["GET"])
def get_single_restaurant(id):
    restaurants_data = get_data_from_db()
    restaurant = get_restaurant_by_id(id, restaurants_data)

    if restaurant:
        desired_order = [
            "id",
            "nama",
            "description",
            "photoUrl",
            "estimatePrice",
            "lat",
            "lon",
        ]
        sorted_restaurant = {key: restaurant[key] for key in desired_order}
        return jsonify({"listKuliner": sorted_restaurant})
    else:
        return jsonify({"error": "Restaurant not found"}), 404


@app.route("/api/culinary/recommendations", methods=["GET"])
def recommend_food():
    latitude = request.args.get("lat")
    longitude = request.args.get("lon")

    if latitude is None or longitude is None:
        return (
            jsonify({"error": "Latitude and longitude parameters are required."}),
            400,
        )

    try:
        latitude = float(latitude)
        longitude = float(longitude)
    except ValueError:
        return jsonify({"error": "Latitude and longitude must be float values."}), 400

    # Data restoran dari database
    restaurants_data = get_data_from_db()

    # Path menuju file TensorFlow L ite model
    model_path = "temp_model.tflite"

    recommended_food = get_food_recommendations(
        latitude, longitude, restaurants_data, model_path
    )
    return jsonify({"listKuliner": recommended_food})


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 8080)))
