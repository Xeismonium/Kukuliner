package com.bangkit.kukuliner.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class CulinaryResponse(
	@field:SerializedName("listKuliner")
	val listKuliner: List<CulinaryResponseItem>
)

@Entity(tableName = "culinary")
@Parcelize
data class CulinaryResponseItem(

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("estimatePrice")
	val estimatePrice: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("lat")
	val lat: Double,

	@ColumnInfo(name = "isFavorite")
	var isFavorite: Boolean = false
) : Parcelable
