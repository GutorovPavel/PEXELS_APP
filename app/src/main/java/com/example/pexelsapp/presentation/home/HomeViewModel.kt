package com.example.pexelsapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.usecases.GetPhotosUseCase
import com.example.pexelsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getPhotos("")
    }

    private fun getPhotos(input: String) {
        getPhotosUseCase(input).onEach {
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

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

//    private val _isSearching = MutableStateFlow(false)
//    val isSearching = _isSearching.asStateFlow()

    private var job: Job? = null

    fun onSearchTextChange(text: String) {
        job?.cancel()
        _searchText.value = text

        job = viewModelScope.launch {
            delay(1000L)
            getPhotos(text)
        }
    }
}