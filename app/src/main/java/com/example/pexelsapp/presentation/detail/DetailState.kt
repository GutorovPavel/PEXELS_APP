package com.example.pexelsapp.presentation.detail

import com.example.pexelsapp.data.remote.dto.Photo

data class DetailState(
    val isLoading: Boolean = false,
    val photo: Photo? = null,
    val error: String = ""
)
