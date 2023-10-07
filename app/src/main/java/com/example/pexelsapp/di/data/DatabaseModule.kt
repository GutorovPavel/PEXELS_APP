package com.example.pexelsapp.di.data

import android.content.Context
import androidx.room.Room
import com.example.pexelsapp.data.local.PexelsDatabase
import com.example.pexelsapp.util.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providePexelsDatabse(
        @ApplicationContext context: Context
    ): PexelsDatabase {
        return Room.databaseBuilder(
            context,
            PexelsDatabase::class.java,
            DB_NAME
        )
            .build()
    }
}