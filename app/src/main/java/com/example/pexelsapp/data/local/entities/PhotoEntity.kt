package com.example.pexelsapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pexelsapp.data.remote.dto.Src
import com.example.pexelsapp.domain.model.Photo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class PhotoEntity(
    val alt: String,
    @SerializedName("avg_color")
    val avgColor: String,
    val height: Int,
    @PrimaryKey val id: Int,
    val liked: Boolean,
    val photographer: String,
    @SerializedName("photographer_id")
    val photographerId: Int,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    @Embedded val src: Src,
    val url: String,
    val width: Int
) {
    fun toPhoto(): Photo = Photo(
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
