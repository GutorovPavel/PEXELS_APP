package com.example.pexelsapp.data.remote.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OnlineInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl.Builder()
            .maxStale(5, TimeUnit.SECONDS)
            .build()

        return response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}