package com.x3vo.backlogtrackr.room

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreDAO
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformDao
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class BacklogTrackrDBRepository (
    private val platformDao: PlatformDao,
    private val pcStoreDAO: PCStoreDAO,
    private val dataStore: DataStore<Preferences>,
    appScope: CoroutineScope
) {

    private val INITIALIZED_KEY = booleanPreferencesKey("platforms_initialized")

    private val defaultPlatforms = listOf("PC", "PS5", "Xbox Series X", "Switch", "Mobile").map { PlatformEntity(platformName = it) }
    val defaultPCStores = listOf("Steam", "Epic Games", "GOG", "Ubisoft", "EA").map { PCStoreEntity(pcStoreName = it) }

    private val initializationJob = appScope.launch {
        val preferences = dataStore.data.first()
        val isInitialized = preferences[INITIALIZED_KEY] ?: false

        if (!isInitialized) {
            dataStore.edit { it[INITIALIZED_KEY] = true }

            platformDao.addMultiplePlatforms(defaultPlatforms)
            pcStoreDAO.addMultiplePCStores(defaultPCStores)
        }
    }

    private suspend fun ensureInitialized() {
        initializationJob.join()
    }

    val allPlatforms: Flow<List<PlatformEntity>> = platformDao.getAllPlatformsFlow()
        .onStart {
            ensureInitialized()
        }

    val allPCStores: Flow<List<PCStoreEntity>> = pcStoreDAO.getAllPCStoresFlow()
        .onStart {
            ensureInitialized()
        }

    suspend fun getAllPlatforms(): List<PlatformEntity> {
        ensureInitialized()
        return platformDao.getAllPlatforms()
    }

    suspend fun resetPlatforms() {
        ensureInitialized()
        platformDao.deleteAllPlatforms()
        platformDao.addMultiplePlatforms(defaultPlatforms)
    }

    suspend fun addPlatform(platformName: String)  {
        ensureInitialized()
        val newEntity = PlatformEntity(
            platformName = platformName
        )
        platformDao.addPlatform(newEntity)
    }

    suspend fun deletePlatform(platformName: String)  {
        ensureInitialized()
        platformDao.deletePlatform(platformName)
    }

    suspend fun getAllPCStores(): List<PCStoreEntity> {
        ensureInitialized()
        return pcStoreDAO.getAllPCStores()
    }

    suspend fun resetPCStores() {
        ensureInitialized()
        pcStoreDAO.deleteAllPCStores()
        pcStoreDAO.addMultiplePCStores(defaultPCStores)
    }

    suspend fun addPCStore(storeName: String) {
        ensureInitialized()
        val newEntity = PCStoreEntity(
            pcStoreName = storeName
        )
        pcStoreDAO.addPCStore(newEntity)
    }

    suspend fun deletePCStore(storeName: String) {
        ensureInitialized()
        pcStoreDAO.deletePCStore(storeName)
    }


}