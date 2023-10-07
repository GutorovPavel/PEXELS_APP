package com.example.pexelsapp.di.repository

import com.example.pexelsapp.data.repository.PexelsRepositoryImpl
import com.example.pexelsapp.domain.repository.PexelsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPexelsRepository(
        pexelsRepositoryImpl: PexelsRepositoryImpl
    ): PexelsRepository
}