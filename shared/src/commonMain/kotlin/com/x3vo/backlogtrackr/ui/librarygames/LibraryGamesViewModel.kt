package com.x3vo.backlogtrackr.ui.librarygames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.LibraryGame.GameWithDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class LibraryGamesViewModel(
    private val repository: BacklogTrackrDBRepository
) : ViewModel() {

    val libraryGames: StateFlow<List<GameWithDetails>> = repository.libraryGamesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}