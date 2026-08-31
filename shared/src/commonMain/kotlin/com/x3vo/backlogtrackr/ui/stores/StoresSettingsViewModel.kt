package com.x3vo.backlogtrackr.ui.stores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.pcstores.PCStoreEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StoresSettingsViewModel(
    private val repository: BacklogTrackrDBRepository
): ViewModel() {
    val uiState: StateFlow<StoreSettingsUIState> = repository.allPCStores
        .map { stores -> StoreSettingsUIState(stores = stores) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StoreSettingsUIState(isLoading = true)
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

data class StoreSettingsUIState(
    val isLoading: Boolean = false,
    val stores: List<PCStoreEntity> = emptyList(),
    val error: String? = null
)