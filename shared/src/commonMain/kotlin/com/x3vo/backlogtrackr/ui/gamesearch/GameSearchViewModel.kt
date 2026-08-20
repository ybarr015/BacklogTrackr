package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.network.BacklogAPIRepository
import com.x3vo.backlogtrackr.network.models.Game
import com.x3vo.backlogtrackr.network.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameSearchViewModel(
    private val backlogAPIRepository: BacklogAPIRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(GameSearchUIState())
    val uiState: StateFlow<GameSearchUIState> = _uiState.asStateFlow()


    init {
        handleGameSearchRequest()
    }



    fun handleGameSearchRequest() {
        viewModelScope.launch {
            println("GameSearchViewModel: Starting search for Red Dead Redemption")
            _uiState.update { it.copy(isLoading = true) }

            val response = backlogAPIRepository.gameSearch("Red Dead Redemption")
            println("GameSearchViewModel: Search response received: $response")

            _uiState.update {
                when(response) {
                    is Resource.Success -> {
                        println("GameSearchViewModel: Success with ${response.data.size} items")
                        GameSearchUIState(isLoading = false, gameData = response.data)
                    }
                    is Resource.Error ->  {
                        println("GameSearchViewModel: Error: ${response.message}")
                        GameSearchUIState(error = response.message)
                    }
                    is Resource.Loading -> {
                        GameSearchUIState(isLoading = true)
                    }
                }
            }
        }
    }

}

data class GameSearchUIState (
    val isLoading : Boolean = false,
    val gameData: List<Game> = emptyList(),
    val error: String? = null
)