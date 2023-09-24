package com.example.pexelsapp.domain.repository

import com.example.pexelsapp.data.remote.dto.Photo
import com.example.pexelsapp.data.remote.dto.SearchResultDto

interface PexelsRepository {
    suspend fun getPhotos(input: String): SearchResultDto
    suspend fun getPhotoById(id: Int): Photo
}