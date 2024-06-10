const db = require("./db");

async function getMultiple() {
  const rows = await db.query(
    "SELECT id, name, description, photoUrl, estimatePrice, lat, lon FROM culinary"
  );
  return rows;
}

async function getSingle(id) {
  const row = await db.query(
    `SELECT id, name, description, photoUrl, estimatePrice, lat, lon 
         FROM culinary 
         WHERE id = ?`,
    [id]
  );

  // If data is empty, return an error or handle it as per your requirement
  if (!row) {
    throw new Error("Culinary not found");
  }

  return rows;
}

async function create(table) {
  const result = await db.query(
    `INSERT INTO culinary 
      (name, description, photoUrl, estimatePrice, lat, lon) 
      VALUES 
      ('${table.name}', '${table.description}', '${table.photoUrl}', '${table.estimatePrice}', ${table.lat}, ${table.lon})`
  );

  let message = "Error in creating culinary";

  if (result.affectedRows) {
    message = "Culinary created successfully";
  }

  return { message };
}

async function update(id, culinary) {
  const result = await db.query(
    `UPDATE culinary
    SET name="${culinary.name}", description="${culinary.description}", 
    photoUrl="${culinary.photoUrl}", estimatePrice="${culinary.estimatePrice}", 
    lat=${culinary.lat}, lon=${culinary.lon} 
    WHERE id=${id}`
  );

  let message = "Error in updating culinary";

  if (result.affectedRows) {
    message = "Culinary updated successfully";
  }

  return { message };
}

async function remove(id) {
  const result = await db.query(`DELETE FROM culinary WHERE id=${id}`);

  let message = "Error in deleting culinary";

  if (result.affectedRows) {
    message = "Culinary deleted successfully";
  }

  return { message };
}

module.exports = {
  getMultiple,
  getSingle,
  create,
  update,
  remove,
};
