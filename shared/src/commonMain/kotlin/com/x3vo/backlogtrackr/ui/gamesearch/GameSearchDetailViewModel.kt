package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.network.BacklogAPIRepository
import com.x3vo.backlogtrackr.network.utils.Resource
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.ui.common.components.BasicDataUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptySet

class GameSearchDetailViewModel(
    private val gameId: String,
    private val backlogAPIRepository: BacklogAPIRepository,
    private val backlogTrackrDBRepository: BacklogTrackrDBRepository
): ViewModel() {

    //this flow contains the data about the game fetch from the server
    private val _gameDetailState = MutableStateFlow(BasicDataUiState<GameSearchObject>())
    val gameDetailState: StateFlow<BasicDataUiState<GameSearchObject>> = _gameDetailState.asStateFlow()

    //this flow contains the user selections made on the screen and sources the options from the database
    private val _selectionsState = MutableStateFlow(UserSelectionsState())
    val selectionsState: StateFlow<UserSelectionsState> = _selectionsState.asStateFlow()

    init {
        loadGameDetails()
        observePlatforms()
        observePcStores()
        observeGameProgressOptions()
    }

    private fun loadGameDetails() {
        viewModelScope.launch {
            println("GameSearchViewModel: Starting search for $gameId")
            _gameDetailState.update { it.copy(isLoading = true) }

            val gameDeferred = async { backlogAPIRepository.gameSearchById(gameId) }
            val timeToBeatDeferred = async { backlogAPIRepository.getTimeToBeat(gameId) }

            val gameResponse = gameDeferred.await()
            val timeResponse = timeToBeatDeferred.await()

            if (gameResponse is Resource.Success) {
                val game = gameResponse.data.firstOrNull()
                val timeData = if (timeResponse is Resource.Success) timeResponse.data.firstOrNull() else null

                if (game != null) {
                    _gameDetailState.update {
                        it.copy(
                            isLoading = false,
                            data = GameSearchObject(
                                id = game.id,
                                name = game.name,
                                coverURL = game.cover?.url ?: "",
                                summary = game.summary ?: "No Summary Available",
                                timeToBeat = timeData?.normally?.let { seconds -> "${seconds / 3600}h" } ?: "N/A"
                            )
                        )
                    }
                } else {
                    _gameDetailState.update { it.copy(isLoading = false, error = "Game not found") }
                }
            } else if (gameResponse is Resource.Error) {
                _gameDetailState.update { it.copy(isLoading = false, error = gameResponse.message) }
            }
        }
    }

    private fun observePlatforms() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allPlatforms.collect { platforms ->
                _selectionsState.update { it.copy(availablePlatforms = platforms.map { it.platformName }) }
            }
        }
    }

    private fun observePcStores() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allPCStores.collect { stores ->
                _selectionsState.update { it.copy(availablePcStores = stores.map { it.pcStoreName }) }
            }
        }
    }

    private fun observeGameProgressOptions() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allGameProgressOptions.collect { progressOptions ->
                val progressOptions = progressOptions.map { it.progressOption }
                _selectionsState.update { state ->
                    state.copy(
                        availableProgressOptions = progressOptions,
                        // If nothing is selected yet, pick the first available option
                        selectedProgressOption = if (state.selectedProgressOption.isEmpty() && progressOptions.isNotEmpty()) {
                            progressOptions[0]
                        } else {
                            state.selectedProgressOption
                        }
                    )
                }
            }
        }
    }

    fun updateOwnership(owned: Boolean) {
        _selectionsState.update { it.copy(isOwned = owned) }
    }

    fun updatePlatform(platform: String, selected: Boolean) {
        _selectionsState.update { state ->
            val updatedPlatforms = if (selected) {
                state.selectedPlatforms + platform
            } else {
                state.selectedPlatforms - platform
            }
            // Clear PC stores if PC is unselected
            val pcStores = if (updatedPlatforms.contains("PC")) state.selectedStores else emptySet()
            state.copy(selectedPlatforms = updatedPlatforms, selectedStores = pcStores)
        }
    }

    fun updatePcStore(store: String, selected: Boolean) {
        _selectionsState.update { state ->
            val updatedStores = if (selected) {
                state.selectedStores + store
            } else {
                state.selectedStores - store
            }
            state.copy(selectedStores = updatedStores)
        }
    }

    fun updateProgress(progress: String) {
        _selectionsState.update { state ->
            state.copy(selectedProgressOption = progress)
        }
    }
}

data class UserSelectionsState(
    val availablePlatforms: List<String> = emptyList(),
    val availablePcStores: List<String> = emptyList(),
    val availableProgressOptions: List<String> = emptyList(),
    val isOwned: Boolean = true,
    val selectedPlatforms: Set<String> = emptySet(),
    val selectedStores: Set<String> = emptySet(),
    val selectedProgressOption: String = ""
)
