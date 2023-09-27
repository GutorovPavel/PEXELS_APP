package com.example.pexelsapp.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsapp.data.local.entities.PhotoEntity


@Database(
    entities = [
        PhotoEntity::class
    ],
    version = 1,
    exportSchema = true,
)
abstract class PexelsDatabase: RoomDatabase() {
    abstract val dao: PexelsDao
}