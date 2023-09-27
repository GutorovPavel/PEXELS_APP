package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.model.Photo
import com.example.pexelsapp.domain.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val repository: PexelsRepository
) {
    operator fun invoke(): Flow<List<Photo>> {
        val localPhotos = repository.getBookmarks()
        return localPhotos.map { list ->
            list.map { photoEntity ->
                photoEntity.toPhoto()
            }
        }
    }
}