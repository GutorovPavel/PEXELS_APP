package com.example.pexelsapp.presentation.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.usecases.GetFeaturedUseCase
import com.example.pexelsapp.domain.usecases.GetPhotosUseCase
import com.example.pexelsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val getFeaturedUseCase: GetFeaturedUseCase
): ViewModel() {

    private val _photoState = mutableStateOf(HomeState())
    val photoState: State<HomeState> = _photoState
    private val _featuredState = mutableStateOf(FeaturedState())
    val featuredState: State<FeaturedState> = _featuredState

    private val _connection = mutableStateOf(false)
    val connection: State<Boolean> = _connection

//    private val _searchResult = MutableStateFlow(SearchResult(emptyList()))
//    val searchResult = _searchResult.asStateFlow()
//    private val _featured = MutableStateFlow(Featured(emptyList()))
//    val featured = _featured.asStateFlow()

    init {
        getFeatured()
        getPhotos("")
    }

    fun getPhotos(input: String) {
        getPhotosUseCase(input).onEach {
            when(it) {
                is Resource.Success -> {
                    _photoState.value = HomeState(searchResult = it.data)
                }
                is Resource.Error -> {
                    _photoState.value = HomeState(error = it.message ?: "An unexpected error occurred...")
                }
                is Resource.Loading -> {
                    _photoState.value = HomeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun getFeatured() {
        getFeaturedUseCase().onEach {
            when(it) {
                is Resource.Success -> {
                    _featuredState.value = FeaturedState(featured = it.data)
                }
                is Resource.Error -> {
                    _featuredState.value = FeaturedState(error = it.message ?: "An unexpected error occurred...")
                }
                is Resource.Loading -> {
                    _featuredState.value = FeaturedState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

//    private val _isSearching = MutableStateFlow(false)
//    val isSearching = _isSearching.asStateFlow()

    private var job: Job? = null

    fun onSearchTextChangeWithUpdate(text: String, timeToUpdate: Long = 1000L) {
        job?.cancel()
        _searchText.value = text

        job = viewModelScope.launch {
            delay(timeToUpdate)
            getPhotos(text)
        }
    }
    fun onSearchTextChange(text: String) {
        job?.cancel()
        _searchText.value = text
    }
    fun isInternetAvailable(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        if (connectivityManager.activeNetwork != null)
            _connection.value = true
    }
}