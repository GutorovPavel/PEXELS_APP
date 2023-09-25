package com.example.pexelsapp.data.remote.dto

import com.example.pexelsapp.domain.model.Featured

data class FeaturedDto(
    val collections: List<Collection>,
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val prev_page: String,
    val total_results: Int
) {
    fun toFeatured(): Featured
        = Featured(collections)
}