package com.example.pexelsapp.data.remote.dto

data class Collection(
    val description: Any,
    val id: String,
    val media_count: Int,
    val photos_count: Int,
    val private: Boolean,
    val title: String,
    val videos_count: Int
)