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

            val response = backlogAPIRepository.gameSearchById(gameId)

            _uiState.update {
                when(response) {
                    is Resource.Success -> {
                        println("GameSearchViewModel: Success with ${response.data.size} items")
                        GameSearchDetailUIState(
                            isLoading = false,
                            gameData = response.data.map {
                                DetailGameObject(
                                    id = it.id,
                                    name =  it.name,
                                    coverURL = it.cover?.url ?: "",
                                    summary = it.summary ?: "No Summary Available"
                                )
                            }
                        )
                    }
                    is Resource.Error ->  {
                        println("GameSearchViewModel: Error: ${response.message}")
                        GameSearchDetailUIState(error = response.message)
                    }
                    is Resource.Loading -> {
                        GameSearchDetailUIState(isLoading = true)
                    }
                }
            }
        }
    }
}

data class DetailGameObject(
    val id: ULong,
    val name: String,
    val coverURL: String,
    val summary: String
)

data class GameSearchDetailUIState (
    val isLoading : Boolean = false,
    val gameData: List<DetailGameObject> = emptyList(),
    val error: String? = null
)