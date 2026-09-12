package com.x3vo.backlogtrackr.ui.library_games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgress
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.domain.model.toGameProgressModel
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryGameDetailViewModel (
    private val gameId: Long,
    private val backlogTrackrDBRepository: BacklogTrackrDBRepository
): ViewModel()
{
    private val _libraryGame = MutableStateFlow(LibraryGameDetailModel())
    val libraryGame: StateFlow<LibraryGameDetailModel> = _libraryGame.asStateFlow()

    private val _libraryGameOptions = MutableStateFlow(LibraryGameDetailOptions())
    val libraryGameOptions: StateFlow<LibraryGameDetailOptions> = _libraryGameOptions.asStateFlow()

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