package com.example.pexelsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pexelsapp.data.downloader.DownloaderImpl
import com.example.pexelsapp.data.local.PexelsDatabase

import com.example.pexelsapp.data.remote.PexelsApi
import com.example.pexelsapp.data.remote.interceptors.OfflineInterceptor
import com.example.pexelsapp.data.remote.interceptors.OnlineInterceptor
import com.example.pexelsapp.data.repository.PexelsRepositoryImpl
import com.example.pexelsapp.domain.downloader.Downloader
import com.example.pexelsapp.domain.repository.PexelsRepository
import com.example.pexelsapp.util.Constants.BASE_URL
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
object AppModule {

    @Provides
    @Singleton
    fun provideDownloader(@ApplicationContext context: Context): Downloader
        = DownloaderImpl(context)
}