package com.x3vo.backlogtrackr.ui.stores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.ui.common.components.BasicListUIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StoresSettingsViewModel(
    private val repository: BacklogTrackrDBRepository
): ViewModel() {
    val uiState: StateFlow<BasicListUIState<PCStoreEntity>> = repository.allPCStores
        .map { stores -> BasicListUIState(data = stores) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BasicListUIState(isLoading = true)
        )

    fun addStore(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.addPCStore(name)
        }
    }

    fun deleteStore(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.deletePCStore(name)
        }
    }

    fun resetStores() {
        viewModelScope.launch {
            repository.resetPCStores()
        }
    }
}