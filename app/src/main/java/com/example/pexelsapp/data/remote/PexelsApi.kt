package com.example.pexelsapp.data.remote

import com.example.pexelsapp.data.remote.dto.SearchResultDto
import com.example.pexelsapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header

interface PexelsApi {
    @GET("v1/search?query=nature")
    suspend fun getPhotos(@Header("Authorization") token: String = API_KEY): SearchResultDto
}