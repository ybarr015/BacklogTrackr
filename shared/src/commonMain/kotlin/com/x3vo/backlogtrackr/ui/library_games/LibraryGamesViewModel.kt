package com.x3vo.backlogtrackr.ui.library_games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.domain.model.toPlatformModel
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
        filterUiState.copy(
            availablePlatforms = allPlatforms,
            availableProgressStates = allProgressStates,
            availablePcStores = allPcStores,
            availableSortOptions = repository.defaultSortOptions,
            availableOrderOptions = repository.defaultOrderOptions
        )
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

        if(platformFilters.isNotEmpty()) { // if there are platform filters
            filteredGames = filteredGames.filter { game ->
                game.platforms.any { platform -> // check all the platforms of the game

                    //if a platform of a game is inside the platform filters set then it should be shown, more complex for pc case
                    if (platformFilters.contains(platform)) {
                        //if the current platform is pc and there are pc store filters, then also filter by those
                        if(platform == DefaultPlatforms.PC.toPlatformModel() && pcStoreFilters.isNotEmpty()) {
                            game.pcStores.any { pcStore ->
                                pcStoreFilters.contains(pcStore)
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
                progressFilters.contains(game.progressOption)
            }
        }

        filteredGames = when (filterUiState.selectedSortOption){
            BacklogTrackrDBRepository.SortOptions.NAME -> {
                if(filterUiState.selectedOrderOption == BacklogTrackrDBRepository.OrderOptions.ASC) {
                    filteredGames.sortedBy { it.name }
                } else {
                    filteredGames.sortedByDescending { it.name }
                }
            }
            BacklogTrackrDBRepository.SortOptions.TIME_TO_BEAT -> {
                if(filterUiState.selectedOrderOption == BacklogTrackrDBRepository.OrderOptions.ASC) {
                    filteredGames.sortedBy { it.timeToBeat.filter { char -> char.isDigit() }.toIntOrNull() ?: 0 }
                } else {
                    filteredGames.sortedByDescending { it.timeToBeat.filter { char -> char.isDigit() }.toIntOrNull() ?: 0 }
                }
            }
            BacklogTrackrDBRepository.SortOptions.ADDED_AT -> {
                if(filterUiState.selectedOrderOption == BacklogTrackrDBRepository.OrderOptions.ASC) {
                    filteredGames.sortedBy { it.addedAt }
                } else {
                    filteredGames.sortedByDescending { it.addedAt }
                }
            }
        }

        filteredGames
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )



    fun onPlatformFilterSelected(platformFilter: GameAttribute.PlatformModel) {
        _filterUiState.update { currentUiState ->
            val updatedPlatformFilter = if (currentUiState.selectedPlatformFilters.contains(platformFilter)) { // if filter is already in the set remove it, otherwise add it
                currentUiState.selectedPlatformFilters - platformFilter
            } else {
                currentUiState.selectedPlatformFilters + platformFilter
            }

            val updatedPcStoreFilter = if(platformFilter == DefaultPlatforms.PC.toPlatformModel() && currentUiState.selectedPlatformFilters.contains(platformFilter)) {
                emptySet()
            } else {
                currentUiState.selectedPcStoreFilters
            }
            currentUiState.copy(selectedPlatformFilters = updatedPlatformFilter, selectedPcStoreFilters = updatedPcStoreFilter)
        }

    }

    fun onProgressFilterSelected(progressFilter: GameAttribute.GameProgressModel) {
        _filterUiState.update { currentUiState ->
            val updatedProgressFilter  = if (currentUiState.selectedProgressStateFilters.contains(progressFilter)) { // if filter is already in the set remove it, otherwise add it
                currentUiState.selectedProgressStateFilters - progressFilter
            } else {
                currentUiState.selectedProgressStateFilters + progressFilter
            }
            currentUiState.copy(selectedProgressStateFilters = updatedProgressFilter)
        }
    }

    fun onPcStoreFilterSelected(pcStoreFilter: GameAttribute.PCStoreModel) {
        _filterUiState.update { currentUiState ->
            val updatedPcStoreFilter = if(currentUiState.selectedPcStoreFilters.contains(pcStoreFilter)) {
                currentUiState.selectedPcStoreFilters - pcStoreFilter
            } else {
                currentUiState.selectedPcStoreFilters + pcStoreFilter
            }
            currentUiState.copy(selectedPcStoreFilters = updatedPcStoreFilter)

        }
    }

    fun onSortOptionSelected(sortOption: BacklogTrackrDBRepository.SortOptions) {
        _filterUiState.update { currentUiState ->
            currentUiState.copy(
                selectedSortOption = sortOption
            )
        }
    }

    fun onOrderOptionSelected(orderOption: BacklogTrackrDBRepository.OrderOptions) {
        _filterUiState.update { currentUiState ->
            currentUiState.copy(
                selectedOrderOption = orderOption
            )
        }
    }


}

data class FilterUiState(
    val availablePlatforms: Set<GameAttribute.PlatformModel> = emptySet(),
    val selectedPlatformFilters: Set<GameAttribute.PlatformModel> = emptySet(),
    val availableProgressStates: Set<GameAttribute.GameProgressModel> = emptySet(),
    val selectedProgressStateFilters: Set<GameAttribute.GameProgressModel> = emptySet(),
    val availablePcStores: Set<GameAttribute.PCStoreModel> = emptySet(),
    val selectedPcStoreFilters: Set<GameAttribute.PCStoreModel> = emptySet(),
    val availableSortOptions: Set<BacklogTrackrDBRepository.SortOptions> = emptySet(),
    val selectedSortOption: BacklogTrackrDBRepository.SortOptions = BacklogTrackrDBRepository.SortOptions.NAME,
    val availableOrderOptions: Set<BacklogTrackrDBRepository.OrderOptions> = emptySet(),
    val selectedOrderOption: BacklogTrackrDBRepository.OrderOptions = BacklogTrackrDBRepository.OrderOptions.ASC
)