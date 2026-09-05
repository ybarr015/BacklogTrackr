package com.x3vo.backlogtrackr.ui.settings.stores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StoresSettingsViewModel(
    private val repository: BacklogTrackrDBRepository
): ViewModel() {
    val stores: StateFlow<List<GameAttribute.PCStoreModel>> = repository.allPCStoresFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
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