package com.example.pexelsapp.domain.model

import com.example.pexelsapp.data.remote.dto.Collection

data class Featured(
    val collections: List<Collection>
)