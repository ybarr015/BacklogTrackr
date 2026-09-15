package com.x3vo.backlogtrackr.ui.library_games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgress
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.domain.model.toGameProgressModel
import com.x3vo.backlogtrackr.domain.model.toPlatformModel
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.plus

class LibraryGameDetailViewModel (
    private val gameId: Long,
    private val backlogTrackrDBRepository: BacklogTrackrDBRepository
): ViewModel()
{
    private val _libraryGame = MutableStateFlow(LibraryGameDetailModel())
    val libraryGame: StateFlow<LibraryGameDetailModel> = _libraryGame.asStateFlow()

    private val _libraryGameOptions = MutableStateFlow(LibraryGameDetailOptions())
    val libraryGameOptions: StateFlow<LibraryGameDetailOptions> = _libraryGameOptions.asStateFlow()

    private val _editGameEvent = MutableSharedFlow<String>()
    val editGameEvent = _editGameEvent.asSharedFlow()

    init {
        loadGameDetails()
        observePlatforms()
        observePcStores()
        observeProgressOptions()
    }

    private fun loadGameDetails() {
        viewModelScope.launch {
            val game = backlogTrackrDBRepository.getLibraryGame(gameId)
            _libraryGame.update {
                game
            }
            _libraryGameOptions.update {
                it.copy(
                    isOwned = _libraryGame.value.isOwned,
                    selectedPlatforms = _libraryGame.value.platforms,
                    selectedStores = _libraryGame.value.pcStores,
                    selectedProgressOption = _libraryGame.value.progressOption
                )
            }
        }
    }

    private fun observePlatforms() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allPlatformsFlow.collect { platforms ->
                _libraryGameOptions.update { it.copy(availablePlatforms = platforms) }
            }
        }
    }
    private fun observePcStores() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allPCStoresFlow.collect { stores ->
                _libraryGameOptions.update { it.copy(availablePcStores = stores) }
            }
        }
    }
    private fun observeProgressOptions() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allGameProgressOptionsFlow.collect { progressOptions ->
                _libraryGameOptions.update { it.copy(availableProgressOptions = progressOptions) }
            }
        }
    }

    fun updateOwnershipSelection(owned: Boolean) {
        _libraryGameOptions.update { current ->
            current.copy(isOwned = owned)
        }
    }

    fun updatePlatformSelection(platform: GameAttribute.PlatformModel, selected: Boolean) {
        _libraryGameOptions.update { state ->
            val updatedPlatformSelections = if(selected) {
                state.selectedPlatforms + platform
            } else {
                state.selectedPlatforms - platform
            }
            val pcStores = if (updatedPlatformSelections.contains(DefaultPlatforms.PC.toPlatformModel()))
                state.selectedStores else emptySet()
            state.copy(selectedPlatforms = updatedPlatformSelections, selectedStores = pcStores)
        }
    }

    fun updatePcStoreSelection(store: GameAttribute.PCStoreModel, selected: Boolean) {
        _libraryGameOptions.update { state ->
            val updatedStoreSelections = if (selected) {
                state.selectedStores + store
            } else {
                state.selectedStores - store
            }
            state.copy(selectedStores = updatedStoreSelections)
        }
    }

    fun updateProgressSelection(progress: GameAttribute.GameProgressModel) {
        _libraryGameOptions.update { state ->
            state.copy(selectedProgressOption = progress)
        }
    }



    fun deleteGame(gameId: Long) {
        viewModelScope.launch {
            backlogTrackrDBRepository.deleteLibraryGame(gameId)
        }
    }

    fun updateGameDetails() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                val gameData = _libraryGame.value
                val newGameSelections = _libraryGameOptions.value
                if (backlogTrackrDBRepository.checkIfGameExistsInLibrary(gameData.gameId)) {
                    backlogTrackrDBRepository.editLibraryGame(
                        gameId = gameData.gameId,
                        newOwnershipState = newGameSelections.isOwned,
                        newProgressState = newGameSelections.selectedProgressOption,
                        newPlatforms = newGameSelections.selectedPlatforms.toList(),
                        newStores = newGameSelections.selectedStores.toList()
                    )
                    loadGameDetails()
                    _editGameEvent.emit("Game Updated")
                } else {
                    _editGameEvent.emit("Error in database, game not updated")
                }
            }



        }
    }

}

data class LibraryGameDetailOptions(
    val availablePlatforms: Set<GameAttribute.PlatformModel> = emptySet(),
    val availablePcStores: Set<GameAttribute.PCStoreModel> = emptySet(),
    val availableProgressOptions: Set<GameAttribute.GameProgressModel> = emptySet(),
    val isOwned: Boolean = true,
    val selectedPlatforms: Set<GameAttribute.PlatformModel> = emptySet(),
    val selectedStores: Set<GameAttribute.PCStoreModel> = emptySet(),
    val selectedProgressOption: GameAttribute.GameProgressModel = DefaultGameProgress.IN_PROGRESS.toGameProgressModel()
)