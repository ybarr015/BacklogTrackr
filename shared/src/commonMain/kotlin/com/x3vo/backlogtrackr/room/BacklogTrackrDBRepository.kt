package com.x3vo.backlogtrackr.room

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.x3vo.backlogtrackr.room.platforms.PlatformDao
import com.x3vo.backlogtrackr.room.platforms.PlatformEntity
import kotlinx.coroutines.flow.first


class BacklogTrackrDBRepository (
    private val platformDao: PlatformDao,
    private val dataStore: DataStore<Preferences>
) {

    private val INITIALIZED_KEY = booleanPreferencesKey("platforms_initialized")

    private val defaultPlatforms = listOf("PC", "PS5", "Xbox Series X", "Switch", "Mobile")

    suspend fun getAllPlatforms(): List<PlatformEntity> {
        checkAndInitializeDefaults()
        return platformDao.getAllPlatforms()
    }

    private suspend fun checkAndInitializeDefaults() {
        val preferences = dataStore.data.first()
        val isInitialized = preferences[INITIALIZED_KEY] ?: false
        
        if (!isInitialized) {
            resetPlatforms()
            dataStore.edit { it[INITIALIZED_KEY] = true }
        }
    }

    suspend fun resetPlatforms() {
        platformDao.deleteAllPlatforms()
        for (platform in defaultPlatforms) {
            addPlatform(platform)
        }
    }

    suspend fun addPlatform(platformName: String)  {
        val newEntity = PlatformEntity(
            platformName = platformName
        )
        platformDao.addPlatform(newEntity)
    }

    suspend fun deletePlatform(platformName: String)  {
        platformDao.deletePlatform(platformName)
    }
}