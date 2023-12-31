package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.remote.dto.toSearchResult
import com.example.pexelsapp.domain.model.SearchResult
import com.example.pexelsapp.domain.repository.PexelsRepository
import com.example.pexelsapp.util.Errors
import com.example.pexelsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: PexelsRepository
) {
    operator fun invoke(input: String, page: Int? = 1): Flow<Resource<SearchResult>> = flow {
        try {
            emit(Resource.Loading())
            val result =
                if (page != null) repository.getPhotos(input, page).toSearchResult()
                else repository.getPhotos(input, 1).toSearchResult()

            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: Errors.HTTP_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(Errors.IO_ERROR))
        }
    }
}