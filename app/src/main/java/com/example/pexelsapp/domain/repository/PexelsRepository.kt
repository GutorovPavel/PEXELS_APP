package com.example.pexelsapp.domain.repository

import com.example.pexelsapp.data.local.entities.PhotoEntity
import com.example.pexelsapp.data.remote.dto.FeaturedDto
import com.example.pexelsapp.data.remote.dto.PhotoDto
import com.example.pexelsapp.data.remote.dto.SearchResultDto
import kotlinx.coroutines.flow.Flow

interface PexelsRepository {
    suspend fun getPhotos(input: String): SearchResultDto
    suspend fun getPhotoById(id: Int): PhotoDto
    suspend fun getFeatured(): FeaturedDto
    suspend fun insertPhoto(photo: PhotoEntity)
    suspend fun deletePhoto(photo: PhotoEntity)
    suspend fun getBookmarkById(id: Int): PhotoEntity?
    suspend fun isPhotoSaved(id: Int): Boolean
    fun getBookmarks(): Flow<List<PhotoEntity>>
}