package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.lifecycle.ViewModel
import com.x3vo.backlogtrackr.network.BacklogAPIRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameSearchDetailViewModel(
): ViewModel() {

    private val _uiState = MutableStateFlow(GameSearchDetailUIState())
    val uiState: StateFlow<GameSearchDetailUIState> = _uiState.asStateFlow()
}

data class GameSearchDetailUIState (
    val isLoading : Boolean = false,
    val gameData: List<SimpleGameObject> = emptyList(),
    val error: String? = null
)