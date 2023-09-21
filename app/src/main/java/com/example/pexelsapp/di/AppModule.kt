package com.example.pexelsapp.di

import com.example.pexelsapp.data.remote.PexelsApi
import com.example.pexelsapp.data.repository.PexelsRepositoryImpl
import com.example.pexelsapp.domain.repository.PexelsRepository
import com.example.pexelsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun providePexelsApi(): PexelsApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
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
    fun provideRepository(api: PexelsApi): PexelsRepository = PexelsRepositoryImpl(api)
}