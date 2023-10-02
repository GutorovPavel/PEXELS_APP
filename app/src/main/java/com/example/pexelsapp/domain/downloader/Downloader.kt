package com.example.pexelsapp.domain.downloader

interface Downloader {
    fun download(url: String): Long
}