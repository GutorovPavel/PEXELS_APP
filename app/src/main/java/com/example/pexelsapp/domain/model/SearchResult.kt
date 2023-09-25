package com.example.pexelsapp.domain.model

import com.example.pexelsapp.data.remote.dto.Photo


data class SearchResult(
//    val next_page: String,
//    val page: Int,
//    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int? = 0
)
