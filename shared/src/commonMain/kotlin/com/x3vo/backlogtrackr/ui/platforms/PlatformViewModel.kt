package com.x3vo.backlogtrackr.ui.platforms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.platforms.PlatformEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlatformViewModel(
    private val repository: BacklogTrackrDBRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlatformUIState())
    val uiState: StateFlow<PlatformUIState> = _uiState.asStateFlow()

    init {
        loadPlatforms()
    }

    private fun loadPlatforms() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val platforms = repository.getAllPlatforms()
            _uiState.update { it.copy(isLoading = false, platforms = platforms) }
        }
    }

    fun addPlatform(name: String) {
        if (name.isBlank())
            return
        viewModelScope.launch {
            repository.addPlatform(name)
            loadPlatforms()
        }
    }

    fun deletePlatform(name: String) {
        if (name.isBlank())
            return
        viewModelScope.launch {
            repository.deletePlatform(name)
            loadPlatforms()
        }
    }

    fun resetPlatforms() {
        viewModelScope.launch {
            repository.resetPlatforms()
            loadPlatforms()
        }
    }

}

data class PlatformUIState(
    val isLoading: Boolean = false,
    val platforms: List<PlatformEntity> = emptyList(),
    val error: String? = null
)
