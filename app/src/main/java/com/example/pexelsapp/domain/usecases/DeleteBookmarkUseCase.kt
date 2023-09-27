package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.model.Photo
import com.example.pexelsapp.domain.repository.PexelsRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val repository: PexelsRepository
) {

    suspend operator fun invoke(photo: Photo) {
        repository.deletePhoto(photo.toPhotoEntity())
    }
}