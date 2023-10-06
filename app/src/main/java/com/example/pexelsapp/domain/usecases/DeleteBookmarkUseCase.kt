package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.repository.PexelsRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val repository: PexelsRepository
) {

    suspend operator fun invoke(id: Int) {
        repository.deletePhoto(id)
    }
}