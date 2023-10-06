package com.example.pexelsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsapp.data.local.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PexelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity)

    @Query("DELETE FROM photos WHERE id = :id")
    suspend fun deletePhoto(id: Int)

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getBookmarkById(id: Int): PhotoEntity?

    @Query("SELECT * FROM photos")
    fun getBookmarks(): Flow<List<PhotoEntity>>
}