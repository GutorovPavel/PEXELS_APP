package com.example.pexelsapp.di.data

import android.content.Context
import com.example.pexelsapp.data.remote.PexelsApi
import com.example.pexelsapp.data.remote.interceptors.OfflineInterceptor
import com.example.pexelsapp.data.remote.interceptors.OnlineInterceptor
import com.example.pexelsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providePexelsApi(
        @ApplicationContext context: Context
    ): PexelsApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //Cache
        val cacheSize = (100 * 1024 * 1024).toLong() // 100 MB
        val cache = Cache(context.cacheDir, cacheSize)
        val offlineInterceptor = OfflineInterceptor(context)
        val onlineInterceptor = OnlineInterceptor()

        val client = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(offlineInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApi::class.java)
    }
}