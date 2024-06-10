package com.bangkit.kukuliner.data.api

import com.bangkit.kukuliner.data.response.CulinaryResponse
import com.bangkit.kukuliner.data.response.CulinaryResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("culinary")
    suspend fun getCulinary(
    ): List<CulinaryResponseItem>
}