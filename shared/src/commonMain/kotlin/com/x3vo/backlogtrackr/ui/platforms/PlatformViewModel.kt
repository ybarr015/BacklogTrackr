package com.x3vo.backlogtrackr.ui.platforms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.platforms.PlatformEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlatformViewModel(
    private val repository: BacklogTrackrDBRepository
) : ViewModel() {

    val uiState: StateFlow<PlatformUIState> = repository.allPlatforms
        .map { platforms -> PlatformUIState(platforms = platforms) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlatformUIState(isLoading = true)
        )

    fun addPlatform(name: String) {
        if (name.isBlank())
            return
        viewModelScope.launch {
            repository.addPlatform(name)
        }
    }

    fun deletePlatform(name: String) {
        if (name.isBlank())
            return
        viewModelScope.launch {
            repository.deletePlatform(name)
        }
    }

    fun resetPlatforms() {
        viewModelScope.launch {
            repository.resetPlatforms()
        }
    }

}

data class PlatformUIState(
    val isLoading: Boolean = false,
    val platforms: List<PlatformEntity> = emptyList(),
    val error: String? = null
)
