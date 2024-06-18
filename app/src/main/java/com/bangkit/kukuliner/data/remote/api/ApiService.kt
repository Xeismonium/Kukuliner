package com.bangkit.kukuliner.data.remote.api

import com.bangkit.kukuliner.data.remote.response.CulinaryResponse
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/culinary")
    suspend fun getAllCulinary(
    ): CulinaryResponse

    @GET("api/culinary/search")
    suspend fun searchCulinary(
        @Query("name") name: String
    ): CulinaryResponse

    @GET("api/culinary/recommendations")
    suspend fun getRecommendationsCulinary(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): CulinaryResponse

}