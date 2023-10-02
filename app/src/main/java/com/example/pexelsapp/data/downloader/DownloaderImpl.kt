package com.example.pexelsapp.data.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.pexelsapp.domain.downloader.Downloader
import com.example.pexelsapp.util.Constants
import javax.inject.Inject

class DownloaderImpl @Inject constructor(
    private val context: Context
): Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun download(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setTitle("${System.currentTimeMillis()}.jpeg")
            .addRequestHeader("Authorization", Constants.API_KEY)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "image.jpeg")
        return downloadManager.enqueue(request)
    }
}