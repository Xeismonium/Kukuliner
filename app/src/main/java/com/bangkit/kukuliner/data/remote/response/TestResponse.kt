package com.bangkit.kukuliner.data.remote.response

import com.google.gson.annotations.SerializedName

data class TestResponse(

	@field:SerializedName("listKuliner")
	val listKuliner: List<ListKulinerItem?>? = null
)

data class ListKulinerItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("estimatePrice")
	val estimatePrice: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)
