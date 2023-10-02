package com.example.pexelsapp.domain.downloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DownloadCompleteReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id != -1L) {
                println("Donload with ID:$id finished!")
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}