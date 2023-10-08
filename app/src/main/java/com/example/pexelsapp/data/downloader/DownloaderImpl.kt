package com.example.pexelsapp.data.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.pexelsapp.domain.downloader.Downloader
import com.example.pexelsapp.util.Constants
import java.io.File
import javax.inject.Inject

class DownloaderImpl @Inject constructor(
    private val context: Context
): Downloader {

    private var directory = File(Environment.DIRECTORY_DOWNLOADS)
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun download(url: String): Long {

        val title = url.substring(url.lastIndexOf("/") + 1) + "1"

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/*")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setTitle(title)
            .addRequestHeader("Authorization", Constants.API_KEY)
            .setDestinationInExternalPublicDir(directory.toString(), title)
        return downloadManager.enqueue(request)
    }
}