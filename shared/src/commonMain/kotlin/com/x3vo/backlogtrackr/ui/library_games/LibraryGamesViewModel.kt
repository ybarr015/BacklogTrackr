package com.x3vo.backlogtrackr.ui.library_games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LibraryGamesViewModel(
    private val repository: BacklogTrackrDBRepository
) : ViewModel() {


    private val _searchKeyword = MutableStateFlow("")
    val searchKeyword = _searchKeyword.asStateFlow()

    fun updateSearchKeyword(newSearchTerm: String) {
        _searchKeyword.update {
            newSearchTerm
        }
    }

    private val _filterUiState = MutableStateFlow(FilterUiState())


    val filterUiState: StateFlow<FilterUiState> = combine(
        repository.allPlatformsFlow,
        repository.allGameProgressOptionsFlow,
        repository.allPCStoresFlow,
        _filterUiState
    ) { allPlatforms, allProgressStates, allPcStores, filterUiState ->
        filterUiState.copy(availablePlatforms = allPlatforms, availableProgressStates = allProgressStates, availablePcStores = allPcStores)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FilterUiState()
    )



    val filteredLibraryGames: StateFlow<List<LibraryGameDetailModel>> = combine(
        repository.allLibraryGamesFlow,
        _filterUiState,
        _searchKeyword
    ) { allGames, filterUiState, searchKeyword ->
        val platformFilters = filterUiState.selectedPlatformFilters
        val pcStoreFilters = filterUiState.selectedPcStoreFilters
        val progressFilters = filterUiState.selectedProgressStateFilters

        var filteredGames = allGames // store all games before making changes
        //apply search text filter first
        filteredGames = filteredGames.filter { game ->
            game.name.contains(searchKeyword, ignoreCase = true)
        }

        if (platformFilters.isEmpty() && progressFilters.isEmpty()) { // if nothing is selected then show all games
            filteredGames
        } else {
            if(platformFilters.isNotEmpty()) { // if there are platform filters
                filteredGames = filteredGames.filter { game ->
                    game.platforms.any { platform -> // check all the platforms of the game

                        //if a platform of a game is inside the platform filters set then it should be shown, more complex for pc case
                        if (platformFilters.contains(platform.platformName)) {
                            //if the current platform is pc and there are pc store filters, then also filter by those
                            if(platform.platformName == DefaultPlatforms.PC.platformName && pcStoreFilters.isNotEmpty()) {
                                game.pcStores.any { pcStore ->
                                    pcStoreFilters.contains(pcStore.pcStoreName)
                                }
                            } else // if the game is not pc , then we return true because we already checked that the game has a platform that was selected
                                true
                        } else { // if the game had no platforms that were selected return false
                            false
                        }
                    }
                }
            }

            //then by progress state
            if (progressFilters.isNotEmpty()) {
                filteredGames = filteredGames.filter { game ->
                    progressFilters.contains(game.progressOption.progressName)
                }
            }

            filteredGames
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )



    fun onPlatformFilterSelected(filterName: String) {
        _filterUiState.update { currentUiState ->
            val updatedPlatformFilter = if (currentUiState.selectedPlatformFilters.contains(filterName)) { // if filter is already in the set remove it, otherwise add it
                currentUiState.selectedPlatformFilters - filterName
            } else {
                currentUiState.selectedPlatformFilters + filterName
            }

            val updatedPcStoreFilter = if(filterName == DefaultPlatforms.PC.platformName && currentUiState.selectedPlatformFilters.contains(filterName)) {
                emptySet()
            } else {
                currentUiState.selectedPcStoreFilters
            }
            currentUiState.copy(selectedPlatformFilters = updatedPlatformFilter, selectedPcStoreFilters = updatedPcStoreFilter)
        }

    }

    fun onProgressFilterSelected(filterName: String) {
        _filterUiState.update { currentUiState ->
            val updatedProgressFilter  = if (currentUiState.selectedProgressStateFilters.contains(filterName)) { // if filter is already in the set remove it, otherwise add it
                currentUiState.selectedProgressStateFilters - filterName
            } else {
                currentUiState.selectedProgressStateFilters + filterName
            }
            currentUiState.copy(selectedProgressStateFilters = updatedProgressFilter)
        }
    }

    fun onPcStoreFilterSelected(filterName: String) {
        _filterUiState.update { currentUiState ->
            val updatedPcStoreFilter = if(currentUiState.selectedPcStoreFilters.contains(filterName)) {
                currentUiState.selectedPcStoreFilters - filterName
            } else {
                currentUiState.selectedPcStoreFilters + filterName
            }
            currentUiState.copy(selectedPcStoreFilters = updatedPcStoreFilter)

        }
    }
}

data class FilterUiState(
    val availablePlatforms: List<GameAttribute.PlatformModel> = emptyList(),
    val selectedPlatformFilters: Set<String> = emptySet(),
    val availableProgressStates: List<GameAttribute.GameProgressModel> = emptyList(),
    val selectedProgressStateFilters: Set<String> = emptySet(),
    val availablePcStores: List<GameAttribute.PCStoreModel> = emptyList(),
    val selectedPcStoreFilters: Set<String> = emptySet()
)