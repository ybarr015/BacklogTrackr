package com.x3vo.backlogtrackr.room.platforms

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query


@Dao
interface PlatformDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlatform(platform: PlatformEntity)

    @Query("DELETE FROM platforms WHERE platformName = :platformName")
    suspend fun deletePlatform(platformName: String)

    @Query("SELECT * FROM platforms")
    suspend fun getAllPlatforms(): List<PlatformEntity>
}