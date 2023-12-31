package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.model.Featured
import com.example.pexelsapp.domain.model.SearchResult
import com.example.pexelsapp.domain.repository.PexelsRepository
import com.example.pexelsapp.util.Errors
import com.example.pexelsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFeaturedUseCase @Inject constructor(
    private val repository: PexelsRepository
) {
    operator fun invoke(): Flow<Resource<Featured>> = flow {

        try {
            emit(Resource.Loading())
            val result = repository.getFeatured().toFeatured()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: Errors.HTTP_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(Errors.IO_ERROR))
        }
    }
}