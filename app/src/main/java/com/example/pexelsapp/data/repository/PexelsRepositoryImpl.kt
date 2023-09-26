package com.example.pexelsapp.data.repository

import android.app.appsearch.SearchResult
import com.example.pexelsapp.data.local.PexelsDao
import com.example.pexelsapp.data.remote.PexelsApi
import com.example.pexelsapp.data.remote.dto.FeaturedDto
import com.example.pexelsapp.data.remote.dto.Photo
import com.example.pexelsapp.data.remote.dto.SearchResultDto
import com.example.pexelsapp.domain.repository.PexelsRepository
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

    override suspend fun getPhotoById(id: Int): Photo {
        return api.getPhotoById(id = id)
    }

    override suspend fun getFeatured(): FeaturedDto {
        return api.getFeatured()
    }
}