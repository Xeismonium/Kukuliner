package com.bangkit.kukuliner.data.remote.api

import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import retrofit2.http.GET

interface ApiService {
    @GET("culinary")
    suspend fun getCulinary(
    ): List<CulinaryResponseItem>
}