package com.example.pexelsapp.data.remote

import com.example.pexelsapp.data.remote.dto.FeaturedDto
import com.example.pexelsapp.data.remote.dto.Photo
import com.example.pexelsapp.data.remote.dto.SearchResultDto
import com.example.pexelsapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {
    @GET("v1/search")
    suspend fun getPhotos(
        @Header("Authorization") token: String = API_KEY,
        @Query("query") input: String,
        @Query("per_page") number: Int = 60
    ): SearchResultDto

    @GET("v1/curated")
    suspend fun getCurated(
        @Header("Authorization") token: String = API_KEY,
        @Query("per_page") number: Int = 60
    ): SearchResultDto

    @GET("v1/photos/{id}")
    suspend fun getPhotoById(
        @Header("Authorization") token: String = API_KEY,
        @Path("id") id: Int
    ): Photo

    @GET("v1/collections/featured")
    suspend fun getFeatured(
        @Header("Authorization") token: String = API_KEY,
        @Query("per_page") number: Int = 7
    ): FeaturedDto
}