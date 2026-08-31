package com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PCStoreDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPCStore(store: PCStoreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMultiplePCStores(stores: List<PCStoreEntity>)

    @Query("DELETE FROM PCStores WHERE pcStoreName = :storeName")
    suspend fun deletePCStore(storeName: String)

    @Query("DELETE FROM PCStores")
    suspend fun deleteAllPCStores()

    @Query("SELECT * FROM PCStores")
    suspend fun getAllPCStores(): List<PCStoreEntity>

    @Query("SELECT * FROM PCStores")
    fun getAllPCStoresFlow(): Flow<List<PCStoreEntity>>

}