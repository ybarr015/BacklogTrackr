package com.x3vo.backlogtrackr.room

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgress
import com.x3vo.backlogtrackr.domain.model.DefaultPCStores
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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

    private val defaultPlatforms = DefaultPlatforms.entries.map { PlatformEntity(platformName = it.platformName) }
    private val defaultPCStores = DefaultPCStores.entries.map { PCStoreEntity(pcStoreName = it.storeName) }
    private val defaultGameProgressOptions = DefaultGameProgress.entries.map { GameProgressOptionEntity(progressOption = it.progressName) }


    //todo check if this can be done inside an init block here
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
    val allPlatformsFlow: Flow<List<GameAttribute.PlatformModel>> = platformDao.getAllPlatformsFlow()
        .map { entities ->
            entities.map {
                GameAttribute.PlatformModel(
                    platformName = it.platformName
                )
            }
        }
        .onStart {
            ensureInitialized()
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
    val allPCStoresFlow: Flow<List<GameAttribute.PCStoreModel>> = pcStoreDAO.getAllPCStoresFlow()
        .map { entities ->
            entities.map {
                GameAttribute.PCStoreModel(
                    pcStoreName = it.pcStoreName
                )
            }
        }
        .onStart {
            ensureInitialized()
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
    val allGameProgressOptionsFlow: Flow<List<GameAttribute.GameProgressModel>> = gameProgressOptionsDao.getallGameProgressOptionsFlow()
        .map { entities ->
            entities.map {
                GameAttribute.GameProgressModel(
                    progressName = it.progressOption
                )
            }
        }
        .onStart {
            ensureInitialized()
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

    val  allLibraryGamesFlow:Flow<List<LibraryGameDetailModel>> = libraryGameDao.getAllLibraryGameDetails()
        .map { gamesList ->
            gamesList.map { game ->

                val gameEntity = game.gameData
                LibraryGameDetailModel(
                    gameId = gameEntity.gameId,
                    name = gameEntity.name,
                    coverUrl = gameEntity.coverUrl,
                    timeToBeat = gameEntity.timeToBeat,
                    summary = gameEntity.summary,
                    isOwned = gameEntity.isOwned,
                    progressOption = GameAttribute.GameProgressModel(game.progressOption.progressOption),
                    platforms = game.platforms.map { GameAttribute.PlatformModel(it.platformName) },
                    pcStores = game.pcStores.map { GameAttribute.PCStoreModel(it.pcStoreName) }
                )

            }
        }


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