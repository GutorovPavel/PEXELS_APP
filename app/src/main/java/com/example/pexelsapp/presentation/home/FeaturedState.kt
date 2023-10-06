package com.example.pexelsapp.presentation.home

import com.example.pexelsapp.domain.model.Featured

data class FeaturedState(
    val isLoading: Boolean = false,
    val featured: Featured? = null,
    val error: String = ""
)
