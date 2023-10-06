package com.example.pexelsapp.domain.model

import com.example.pexelsapp.data.remote.dto.PhotoDto


data class SearchResult(
    val next_page: String? = "",
    val page: Int? = 1,
    var photos: List<PhotoDto>,
    val total_results: Int? = 0
)
