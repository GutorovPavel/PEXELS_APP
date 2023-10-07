package com.example.pexelsapp.domain.downloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.pexelsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadCompleteReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id != -1L) {
                Log.d("download","Download with ID:$id finished!")
                Toast.makeText(
                    context,
                    context?.getString(R.string.saved) ?: "saved",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}