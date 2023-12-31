package com.example.pexelsapp.data.remote.dto

import com.example.pexelsapp.domain.model.SearchResult

data class SearchResultDto(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<PhotoDto>,
    val total_results: Int
)

fun SearchResultDto.toSearchResult(): SearchResult
    = SearchResult(next_page, page, photos, total_results)