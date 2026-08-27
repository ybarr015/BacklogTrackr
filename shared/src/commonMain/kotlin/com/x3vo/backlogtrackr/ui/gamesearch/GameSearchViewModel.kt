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

class GameSearchViewModel(
    private val backlogAPIRepository: BacklogAPIRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(GameSearchUIState())
    val uiState: StateFlow<GameSearchUIState> = _uiState.asStateFlow()


    private val _searchKeyword = MutableStateFlow("")
    val searchKeyword = _searchKeyword.asStateFlow()

    fun updateSearchKeyword (newSearchTerm: String) {
        _searchKeyword.update {
            newSearchTerm
        }
    }

    fun handleSearchButtonRequest() {
         handleGameSearchRequest(_searchKeyword.value)
    }

    fun handleGameSearchRequest(gameName: String) {
        viewModelScope.launch {
            println("GameSearchViewModel: Starting search for $gameName")
            _uiState.update { it.copy(isLoading = true) }

            val response = backlogAPIRepository.gameSearchByName(gameName)

            _uiState.update {
                when(response) {
                    is Resource.Success -> {
                        println("GameSearchViewModel: Success with ${response.data.size} items")
                        GameSearchUIState(
                            isLoading = false,
                            gameData = response.data.map {
                                SimpleGameObject(
                                    id = it.id,
                                    name =  it.name,
                                    coverURL = it.cover?.url ?: "",
                                    summary = it.summary?: "No Summary Available"
                                )
                            }
                        )
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

data class SimpleGameObject (
    val id: ULong,
    val name: String,
    val coverURL: String,
    val summary: String
)

data class GameSearchUIState (
    val isLoading : Boolean = false,
    val gameData: List<SimpleGameObject> = emptyList(),
    val error: String? = null
)