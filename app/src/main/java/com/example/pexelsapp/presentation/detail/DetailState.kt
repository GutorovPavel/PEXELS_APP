package com.example.pexelsapp.presentation.detail

import com.example.pexelsapp.domain.model.Photo

data class DetailState(
    val isLoading: Boolean = false,
    val photo: Photo? = null,
    val error: String = ""
)
