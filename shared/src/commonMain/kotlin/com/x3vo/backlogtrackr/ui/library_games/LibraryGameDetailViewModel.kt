package com.x3vo.backlogtrackr.ui.library_games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryGameDetailViewModel (
    private val gameId: Long,
    private val backlogTrackrDBRepository: BacklogTrackrDBRepository
): ViewModel()
{
    private val _libraryGame = MutableStateFlow(LibraryGameDetailModel())
    val libraryGame: StateFlow<LibraryGameDetailModel> = _libraryGame.asStateFlow()

    init {
        loadGameDetails()
    }

    private fun loadGameDetails() {
        viewModelScope.launch {
            _libraryGame.update {
                backlogTrackrDBRepository.getLibraryGame(gameId)
            }
        }
    }

}