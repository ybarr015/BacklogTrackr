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

    @Transaction
    suspend fun editLibraryGame(
        gameId: Long,
        newOwnershipState: Boolean,
        newProgressState: String,
        newPlatforms: List<GamePlatformCrossRef>,
        newStores: List<GamePcStoreCrossRef>
    ) {

        deletePlatforms(gameId)
        deletePcStores(gameId)

        editOwnership(newOwnershipState,gameId)
        editProgressState(newProgressState,gameId)

        insertPlatformsForGame(newPlatforms)
        insertStoresForGame(newStores)
    }

    @Query("UPDATE LibraryGames set isOwned = :updatedValue WHERE gameId = :gameId ")
    suspend fun editOwnership(updatedValue: Boolean, gameId: Long)

    @Query("UPDATE LibraryGames set gameProgress = :updatedValue WHERE gameId = :gameId ")
    suspend fun editProgressState(updatedValue: String, gameId: Long)

    @Query("DELETE FROM game_platform_cross_ref WHERE gameId = :gameId ")
    suspend fun deletePlatforms(gameId: Long)

    @Query("DELETE FROM game_pc_store_cross_ref WHERE gameId = :gameId ")
    suspend fun deletePcStores(gameId: Long)

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