package com.example.pexelsapp.presentation.home

import com.example.pexelsapp.domain.model.SearchResult

data class HomeState(
    val isLoading: Boolean = false,
    val searchResult: SearchResult? = null,
    val error: String = ""
)
