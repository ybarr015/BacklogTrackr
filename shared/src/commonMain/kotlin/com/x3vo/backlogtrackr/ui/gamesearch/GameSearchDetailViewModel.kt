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
                        GameSearchDetailUIState(
                            isLoading = false,
                            gameData = listOf(
                                DetailGameObject(
                                    id = game.id,
                                    name = game.name,
                                    coverURL = game.cover?.url ?: "",
                                    summary = game.summary ?: "No Summary Available",
                                    timeToBeat = timeData?.normally?.let { "${it / 3600}h" } ?: "N/A"
                                )
                            )
                        )
                    }
                } else {
                    _uiState.update { GameSearchDetailUIState(error = "Game not found") }
                }
            } else if (gameResponse is Resource.Error) {
                _uiState.update { GameSearchDetailUIState(error = gameResponse.message) }
            }
        }
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
    val error: String? = null
)