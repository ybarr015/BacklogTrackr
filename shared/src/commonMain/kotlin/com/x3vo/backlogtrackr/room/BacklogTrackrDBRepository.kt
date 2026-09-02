package com.x3vo.backlogtrackr.room

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.x3vo.backlogtrackr.room.LibraryGame.GamePcStoreCrossRef
import com.x3vo.backlogtrackr.room.LibraryGame.GamePlatformCrossRef
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameDao
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionsDao
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
    private val gameProgressOptionsDao: GameProgressOptionsDao,
    private val libraryGameDao: LibraryGameDao,
    private val dataStore: DataStore<Preferences>,
    appScope: CoroutineScope
) {

    private val INITIALIZED_KEY = booleanPreferencesKey("platforms_initialized")

    private val defaultPlatforms = listOf("PC", "PS5", "Xbox Series X", "Switch", "Mobile").map { PlatformEntity(platformName = it) }
    private val defaultPCStores = listOf("Steam", "Epic Games", "GOG", "Ubisoft", "EA").map { PCStoreEntity(pcStoreName = it) }
    private val defaultGameProgressOptions = listOf("Not Started", "In Progress", "Finished", "Dropped").map { GameProgressOptionEntity(progressOption = it) }


    //this initializes the default options if app is first installed
    private val initializationJob = appScope.launch {
        val preferences = dataStore.data.first()
        val isInitialized = preferences[INITIALIZED_KEY] ?: false

        if (!isInitialized) {
            dataStore.edit { it[INITIALIZED_KEY] = true }

            platformDao.addMultiplePlatforms(defaultPlatforms)
            pcStoreDAO.addMultiplePCStores(defaultPCStores)
            gameProgressOptionsDao.addMultipleGameProgressOptions(defaultGameProgressOptions)
        }
    }

    //this joins the initialization job if its required
    private suspend fun ensureInitialized() {
        initializationJob.join()
    }

    //platforms
    val allPlatforms: Flow<List<PlatformEntity>> = platformDao.getAllPlatformsFlow()
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

    //stores
    val allPCStores: Flow<List<PCStoreEntity>> = pcStoreDAO.getAllPCStoresFlow()
        .onStart {
            ensureInitialized()
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

    //gameprogress
    val allGameProgressOptions: Flow<List<GameProgressOptionEntity>> = gameProgressOptionsDao.getallGameProgressOptionsFlow()
        .onStart {
            ensureInitialized()
        }

    suspend fun getAllGameProgressOptions(): List<GameProgressOptionEntity> {
        ensureInitialized()
        return gameProgressOptionsDao.getAllGameProgressOptions()
    }

    suspend fun resetGameProgressOptions() {
        ensureInitialized()
        gameProgressOptionsDao.deleteAllGameProgressOptions()
        gameProgressOptionsDao.addMultipleGameProgressOptions(defaultGameProgressOptions)
    }

    suspend fun addGameProgressOption(progressOption: String) {
        ensureInitialized()
        val newEntity = GameProgressOptionEntity(
            progressOption = progressOption
        )
        gameProgressOptionsDao.addGameProgressOption(newEntity)
    }

    suspend fun deleteGameProgressOption(progressOption: String) {
        ensureInitialized()
        gameProgressOptionsDao.deleteGameProgressOption(progressOption)
    }

    //library game dao
    suspend fun getLibraryGame(id: Long) = libraryGameDao.getLibraryGameDetails(id)

    val libraryGamesFlow = libraryGameDao.getAllLibraryGameDetails()

    suspend fun addLibraryGame(game: LibraryGameEntity, platforms: List<String>, stores: List<String>) {
        val platformLinks = platforms.map {
            GamePlatformCrossRef(gameId = game.gameId, platformName = it)
        }
        val storeLinks = stores.map {
            GamePcStoreCrossRef(gameId = game.gameId, pcStoreName = it)
        }

        libraryGameDao.addLibraryGame(game, platformLinks, storeLinks)
    }

    suspend fun deleteLibraryGame(gameId: Long) = libraryGameDao.deleteLibraryGame(id = gameId)

    suspend fun checkIfGameExistsInLibrary(gameId: Long) = libraryGameDao.checkIfGameExistsInLibrary(gameId)

}