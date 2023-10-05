package com.example.pexelsapp.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pexelsapp.data.local.converters.Converters
import com.example.pexelsapp.data.local.entities.PhotoEntity


@Database(
    entities = [
        PhotoEntity::class
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]
)
abstract class PexelsDatabase: RoomDatabase() {
    abstract val dao: PexelsDao
}