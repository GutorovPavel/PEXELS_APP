package com.example.pexelsapp.data.repository

import com.example.pexelsapp.data.local.PexelsDao
import com.example.pexelsapp.data.local.PexelsDatabase
import com.example.pexelsapp.data.local.entities.PhotoEntity
import com.example.pexelsapp.data.remote.PexelsApi
import com.example.pexelsapp.data.remote.dto.FeaturedDto
import com.example.pexelsapp.data.remote.dto.PhotoDto
import com.example.pexelsapp.data.remote.dto.SearchResultDto
import com.example.pexelsapp.domain.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    private val api: PexelsApi,
    private val db: PexelsDatabase
): PexelsRepository {
    override suspend fun getPhotos(input: String, page: Int): SearchResultDto {
        return if (input != "") {
            api.getPhotos(input = input, page = page)
        } else {
            api.getCurated(page = page)
        }
    }

    override suspend fun getPhotoById(id: Int): PhotoDto {
        return api.getPhotoById(id = id)
    }

    override suspend fun getFeatured(): FeaturedDto {
        return api.getFeatured()
    }

    override suspend fun insertPhoto(photo: PhotoEntity) {
        db.dao.insertPhoto(photo)
    }

    override suspend fun deletePhoto(id: Int) {
        db.dao.deletePhoto(id)
    }

    override suspend fun getBookmarkById(id: Int): PhotoEntity? {
        return db.dao.getBookmarkById(id)
    }

    override suspend fun isPhotoSaved(id: Int): Boolean {
        return db.dao.getBookmarkById(id) != null
    }

    override fun getBookmarks(): Flow<List<PhotoEntity>> {
        return db.dao.getBookmarks()
    }
}