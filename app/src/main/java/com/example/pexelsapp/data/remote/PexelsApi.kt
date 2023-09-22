package com.example.pexelsapp.data.remote

import com.example.pexelsapp.data.remote.dto.SearchResultDto
import com.example.pexelsapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApi {
    @GET("v1/search")
    suspend fun getPhotos(
        @Header("Authorization") token: String = API_KEY,
        @Query("query") input: String
    ): SearchResultDto

    @GET("v1/curated")
    suspend fun getCurated(@Header("Authorization") token: String = API_KEY): SearchResultDto
}