package com.example.pexelsapp.data.remote.dto

import com.example.pexelsapp.domain.model.Photo

data class PhotoDto(
    val alt: String,
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val src: Src,
    val url: String,
    val width: Int
) {
    fun toPhoto(): Photo = Photo(
        alt,
        avg_color,
        height,
        id,
        liked,
        photographer,
        photographer_id,
        photographer_url,
        src,
        url,
        width
    )
}