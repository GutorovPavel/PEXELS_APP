package com.example.pexelsapp.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.model.Photo
import com.example.pexelsapp.domain.usecases.AddBookmarkUseCase
import com.example.pexelsapp.domain.usecases.DeleteBookmarkUseCase
import com.example.pexelsapp.domain.usecases.GetPhotoByIdUseCase
import com.example.pexelsapp.domain.usecases.IsSavedUseCase
import com.example.pexelsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val isSavedUseCase: IsSavedUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    private val _isSaved = mutableStateOf(false)
    val isSaved: State<Boolean> = _isSaved

    init {
        savedStateHandle.get<Int>("photoId")?.let {
            getPhoto(it)
            initIsSaved(it)
        }
    }

    private fun getPhoto(id: Int) {
        getPhotoByIdUseCase(id).onEach {
            when(it) {
                is Resource.Success -> {
                    _state.value = DetailState(photo = it.data)
                }
                is Resource.Error -> {
                    _state.value = DetailState(error = it.message ?: "An unexpected error occurred...")
                }
                is Resource.Loading -> {
                    _state.value = DetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addBookmark(photo: Photo) {
        viewModelScope.launch {
            addBookmarkUseCase(photo)
        }
        setIsSaved(true)
    }

    fun deleteBookmark(photo: Photo) {
        viewModelScope.launch {
            deleteBookmarkUseCase(photo)
        }
        setIsSaved(false)
    }

    private fun initIsSaved(id: Int) {
        viewModelScope.launch {
            _isSaved.value = isSavedUseCase(id)
        }
    }

    private fun setIsSaved(isSaved: Boolean) {
        _isSaved.value = isSaved
    }
}