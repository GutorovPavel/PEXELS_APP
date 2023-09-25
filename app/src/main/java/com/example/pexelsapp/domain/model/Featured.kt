package com.example.pexelsapp.domain.model

import com.example.pexelsapp.data.remote.dto.Collection

data class Featured(
    val collections: List<Collection>,
//    val next_page: String? = "",
//    val page: Int? = 0,
//    val per_page: Int? = 0,
//    val prev_page: String? = "",
//    val total_results: Int? = 0
)