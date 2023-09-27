package com.example.pexelsapp.domain.model

import com.example.pexelsapp.data.local.entities.PhotoEntity
import com.example.pexelsapp.data.remote.dto.Src

data class Photo(
    val alt: String,
    val avgColor: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographerId: Int,
    val photographerUrl: String,
    val src: Src,
    val url: String,
    val width: Int
) {
    fun toPhotoEntity(): PhotoEntity = PhotoEntity(
        alt,
        avgColor,
        height,
        id,
        liked,
        photographer,
        photographerId,
        photographerUrl,
        src,
        url,
        width
    )
}
