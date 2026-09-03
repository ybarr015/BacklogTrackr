package com.x3vo.backlogtrackr.ui.settings.platforms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlatformViewModel(
    private val repository: BacklogTrackrDBRepository
) : ViewModel() {

    val platforms: StateFlow<List<PlatformEntity>> = repository.allPlatformsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
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
