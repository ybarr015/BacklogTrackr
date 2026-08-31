package com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameProgressOptionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameProgressOption(progressOption: GameProgressOptionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMultipleGameProgressOptions(progressOptionList: List<GameProgressOptionEntity>)

    @Query("DELETE FROM GameProgressOptions WHERE progressOption = :progressOption")
    suspend fun deleteGameProgressOption(progressOption: String)

    @Query("DELETE FROM GameProgressOptions")
    suspend fun deleteAllGameProgressOptions()

    @Query("SELECT * FROM GameProgressOptions")
    suspend fun getAllGameProgressOptions(): List<GameProgressOptionEntity>

    @Query("SELECT * FROM GameProgressOptions")
    fun getallGameProgressOptionsFlow(): Flow<List<GameProgressOptionEntity>>
}