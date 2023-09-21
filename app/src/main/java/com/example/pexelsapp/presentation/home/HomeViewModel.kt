package com.example.pexelsapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.usecases.GetPhotosUseCase
import com.example.pexelsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getPhotos()
    }

    private fun getPhotos() {
        getPhotosUseCase().onEach {
            when(it) {
                is Resource.Success -> {
                    _state.value = HomeState(searchResult = it.data)
                }
                is Resource.Error -> {
                    _state.value = HomeState(error = it.message ?: "An unexpected error occurred...")
                }
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}