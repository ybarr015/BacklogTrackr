package com.x3vo.backlogtrackr.ui.settings.gameprogressoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class GameProgressSettingsViewModel (
    private val repository: BacklogTrackrDBRepository
) : ViewModel() {
    val gameProgressOptions: StateFlow<List<GameProgressOptionEntity>> = repository.allGameProgressOptions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addGameProgressOption(progressOption: String) {
        if (progressOption.isBlank()) return
        viewModelScope.launch {
            repository.addGameProgressOption(progressOption)
        }
    }

    fun deleteGameProgressOption(progressOption: String) {
        if(progressOption.isBlank()) return
        viewModelScope.launch {
            repository.deleteGameProgressOption(progressOption)
        }
    }

    fun resetProgressOptions() {
        viewModelScope.launch {
            repository.resetGameProgressOptions()
        }
    }
}