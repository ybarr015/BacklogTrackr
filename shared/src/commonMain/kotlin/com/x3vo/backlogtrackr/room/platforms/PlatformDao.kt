package com.x3vo.backlogtrackr.room.platforms

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PlatformDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlatform(platform: PlatformEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMultiplePlatforms(platform: List<PlatformEntity>)

    @Query("DELETE FROM platforms WHERE platformName = :platformName")
    suspend fun deletePlatform(platformName: String)

    @Query("DELETE FROM platforms")
    suspend fun deleteAllPlatforms()

    @Query("SELECT * FROM platforms")
    suspend fun getAllPlatforms(): List<PlatformEntity>

    @Query("SELECT * FROM platforms")
    fun getAllPlatformsFlow(): Flow<List<PlatformEntity>>
}