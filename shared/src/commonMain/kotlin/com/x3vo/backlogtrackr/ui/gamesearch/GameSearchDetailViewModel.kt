package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.network.BacklogAPIRepository
import com.x3vo.backlogtrackr.network.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlin.collections.emptySet

class GameSearchDetailViewModel(
    private val gameId: String,
    private val backlogAPIRepository: BacklogAPIRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(GameSearchDetailUIState())
    val uiState: StateFlow<GameSearchDetailUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            println("GameSearchViewModel: Starting search for $gameId")
            _uiState.update { it.copy(isLoading = true) }

            val gameDeferred = async { backlogAPIRepository.gameSearchById(gameId) }
            val timeToBeatDeferred = async { backlogAPIRepository.getTimeToBeat(gameId) }

            val gameResponse = gameDeferred.await()
            val timeResponse = timeToBeatDeferred.await()

            if (gameResponse is Resource.Success) {
                val game = gameResponse.data.firstOrNull()
                val timeData = if (timeResponse is Resource.Success) timeResponse.data.firstOrNull() else null

                if (game != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            gameData = listOf(
                                DetailGameObject(
                                    id = game.id,
                                    name = game.name,
                                    coverURL = game.cover?.url ?: "",
                                    summary = game.summary ?: "No Summary Available",
                                    timeToBeat = timeData?.normally?.let { seconds -> "${seconds / 3600}h" } ?: "N/A"
                                )
                            )
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Game not found") }
                }
            } else if (gameResponse is Resource.Error) {
                _uiState.update { it.copy(isLoading = false, error = gameResponse.message) }
            }
        }
    }

    fun updateOwnership(owned: Boolean) {
        _uiState.update { it.copy(isOwned = owned) }
    }

    fun updatePlatform(platform: String, selected: Boolean) {
        _uiState.update { state ->
            val updatedPlatforms = if (selected) {
                state.selectedPlatforms + platform
            } else {
                state.selectedPlatforms - platform
            }
            // Clear PC launchers if PC is unselected
            val pcStores = if (!updatedPlatforms.contains("PC")) emptySet() else state.selectedStore
            state.copy(selectedPlatforms = updatedPlatforms, selectedStore = pcStores)
        }
    }

    fun updatePcStore(store: String, selected: Boolean) {
        _uiState.update { state ->
            val updatedStores = if (selected) {
                state.selectedStore + store
            } else {
                state.selectedStore - store
            }
            state.copy(selectedStore = updatedStores)
        }
    }

    fun updateProgress(progress: String) {
        _uiState.update { it.copy(progress = progress) }
    }
}

data class DetailGameObject(
    val id: ULong,
    val name: String,
    val coverURL: String,
    val summary: String,
    val timeToBeat: String
)

data class GameSearchDetailUIState (
    val isLoading : Boolean = false,
    val gameData: List<DetailGameObject> = emptyList(),
    val error: String? = null,
    val isOwned: Boolean = true,
    val selectedPlatforms: Set<String> = emptySet(),
    val selectedStore: Set<String> = emptySet(),
    val progress: String = "Not Started"
)
