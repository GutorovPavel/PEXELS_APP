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
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePexelsDatabse(app: Application): PexelsDatabase {
        return Room.databaseBuilder(
            app,
            PexelsDatabase::class.java,
            "pexels.db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideDownloader(@ApplicationContext context: Context): Downloader
        = DownloaderImpl(context)

    @Provides
    @Singleton
    fun provideRepository(
        api: PexelsApi,
        db: PexelsDatabase
    ): PexelsRepository = PexelsRepositoryImpl(api, db.dao)
}