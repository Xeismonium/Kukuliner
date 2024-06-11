package com.bangkit.kukuliner.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class CulinaryResponse(
	@field:SerializedName("CulinaryResponse")
	val culinaryResponse: List<CulinaryResponseItem>
)

@Parcelize
data class CulinaryResponseItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("estimatePrice")
	val estimatePrice: String,

	@field:SerializedName("lat")
	val lat: Double
) : Parcelable
