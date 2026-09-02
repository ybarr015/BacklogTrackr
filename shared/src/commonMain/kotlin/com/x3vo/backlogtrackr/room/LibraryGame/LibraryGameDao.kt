package com.x3vo.backlogtrackr.room.LibraryGame

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryGameDao {
    @Transaction
    @Query("SELECT * FROM LibraryGames WHERE gameId = :id")
    suspend fun getLibraryGameDetails(id: Long): GameWithDetails

    @Transaction
    @Query("SELECT * FROM LibraryGames")
    fun getAllLibraryGameDetails(): Flow<List<GameWithDetails>>

    @Transaction
    suspend fun addLibraryGame(
        game:LibraryGameEntity,
        platforms: List<GamePlatformCrossRef>,
        stores: List<GamePcStoreCrossRef>
    ) {
        insertLibraryGame(game)


        insertPlatformsForGame(platforms)


        insertStoresForGame(stores)

    }

    @Upsert
    suspend fun insertLibraryGame(game: LibraryGameEntity)

    @Insert
    suspend fun insertPlatformsForGame(platforms: List<GamePlatformCrossRef>)

    @Insert
    suspend fun insertStoresForGame(stores: List<GamePcStoreCrossRef>)

    @Query("DELETE FROM LibraryGames WHERE gameId = :id")
    suspend fun deleteLibraryGame(id: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM LibraryGames WHERE gameId = :gameId)")
    suspend fun checkIfGameExistsInLibrary(gameId: Long): Boolean
}