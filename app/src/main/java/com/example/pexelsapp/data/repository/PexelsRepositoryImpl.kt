package com.example.pexelsapp.data.repository

import com.example.pexelsapp.data.local.PexelsDao
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
    private val dao: PexelsDao
): PexelsRepository {
    override suspend fun getPhotos(input: String): SearchResultDto {
        return if (input != "") {
            api.getPhotos(input = input)
        } else {
            api.getCurated()
        }
    }

    override suspend fun getPhotoById(id: Int): PhotoDto {
        return api.getPhotoById(id = id)
    }

    override suspend fun getFeatured(): FeaturedDto {
        return api.getFeatured()
    }

    override suspend fun insertPhoto(photo: PhotoEntity) {
        dao.insertPhoto(photo)
    }

    override suspend fun deletePhoto(photo: PhotoEntity) {
        dao.deletePhoto(photo)
    }

    override suspend fun getBookmarkById(id: Int): PhotoEntity? {
        return dao.getBookmarkById(id)
    }

    override suspend fun isPhotoSaved(id: Int): Boolean {
        return dao.getBookmarkById(id) != null
    }

    override fun getBookmarks(): Flow<List<PhotoEntity>> {
        return dao.getBookmarks()
    }
}