package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.remote.dto.Photo
import com.example.pexelsapp.data.remote.dto.toSearchResult
import com.example.pexelsapp.domain.model.SearchResult
import com.example.pexelsapp.domain.repository.PexelsRepository
import com.example.pexelsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetPhotoByIdUseCase @Inject constructor(
    private val repository: PexelsRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Photo>> = flow {

        try {
            emit(Resource.Loading())
            val result = repository.getPhotoById(id)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "an unexpected error occurred..."))
        }
    }
}