package com.example.pexelsapp.domain.usecases

import android.util.Log
import com.example.pexelsapp.domain.model.Photo
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

            val localPhoto = repository.getBookmarkById(id)
            if (localPhoto != null) {
                Log.d("from db", "That photo is from db")
                emit(Resource.Success(localPhoto.toPhoto()))
            } else {
                val result = repository.getPhotoById(id).toPhoto()
                emit(Resource.Success(result))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "an unexpected error occurred..."))
        }
    }
}