package com.example.pexelsapp.data.remote.interceptors

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


private fun isInternetAvailable(context: Context): Boolean {
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    if (connectivityManager.activeNetwork != null)
        isConnected = true
    return isConnected
}
class OfflineInterceptor @Inject constructor(
    private val context: Context
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!isInternetAvailable(context)) {

            val cacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.HOURS)
                .build()

            request = request.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .cacheControl(cacheControl)
                .build()
        }
        return chain.proceed(request)
    }
}