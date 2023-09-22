package com.example.pexelsapp.domain.repository

import android.app.appsearch.SearchResult
import com.example.pexelsapp.data.remote.dto.SearchResultDto

interface PexelsRepository {

    suspend fun getPhotos(input: String): SearchResultDto
}