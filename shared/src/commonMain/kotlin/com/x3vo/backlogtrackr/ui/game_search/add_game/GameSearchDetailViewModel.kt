package com.x3vo.backlogtrackr.ui.game_search.add_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.network.BacklogAPIRepository
import com.x3vo.backlogtrackr.network.utils.Resource
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameEntity
import com.x3vo.backlogtrackr.ui.common.components.BasicDataUiState
import com.x3vo.backlogtrackr.domain.model.GameSearchObject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptySet

class GameSearchDetailViewModel(
    private val gameId: String,
    private val backlogAPIRepository: BacklogAPIRepository,
    private val backlogTrackrDBRepository: BacklogTrackrDBRepository
): ViewModel() {
    private val _gameAddEvent = MutableSharedFlow<GameAddEvent>()
    val gameAddEvent = _gameAddEvent.asSharedFlow()

    //this flow contains the data about the game fetch from the server
    private val _gameDetailState = MutableStateFlow(BasicDataUiState<GameSearchObject>())
    val gameDetailState: StateFlow<BasicDataUiState<GameSearchObject>> = _gameDetailState.asStateFlow()

    //this flow contains the user selections made on the screen and sources the options from the database
    private val _selectionsState = MutableStateFlow(UserSelectionsState())
    val selectionsState: StateFlow<UserSelectionsState> = _selectionsState.asStateFlow()

    init {
        loadGameDetails()
        observePlatforms()
        observePcStores()
        observeGameProgressOptions()
    }

    private fun loadGameDetails() {
        viewModelScope.launch {
            println("GameSearchViewModel: Starting search for $gameId")
            _gameDetailState.update { it.copy(isLoading = true) }

            val gameDeferred = async { backlogAPIRepository.gameSearchById(gameId) }
            val timeToBeatDeferred = async { backlogAPIRepository.getTimeToBeat(gameId) }

            val gameResponse = gameDeferred.await()
            val timeResponse = timeToBeatDeferred.await()

            if (gameResponse is Resource.Success) {
                val game = gameResponse.data.firstOrNull()
                val timeData = if (timeResponse is Resource.Success) timeResponse.data.firstOrNull() else null

                if (game != null) {
                    _gameDetailState.update {
                        it.copy(
                            isLoading = false,
                            data = GameSearchObject(
                                id = game.id,
                                name = game.name,
                                coverURL = game.cover?.url ?: "",
                                summary = game.summary ?: "No Summary Available",
                                timeToBeat = timeData?.normally?.let { seconds -> "${seconds / 3600}h" } ?: "N/A"
                            )
                        )
                    }
                } else {
                    _gameDetailState.update { it.copy(isLoading = false, error = "Game not found") }
                }
            } else if (gameResponse is Resource.Error) {
                _gameDetailState.update { it.copy(isLoading = false, error = gameResponse.message) }
            }
        }
    }

    private fun observePlatforms() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allPlatformsFlow.collect { platforms ->
                _selectionsState.update { it.copy(availablePlatforms = platforms) }
            }
        }
    }

    private fun observePcStores() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allPCStoresFlow.collect { stores ->
                _selectionsState.update { it.copy(availablePcStores = stores) }
            }
        }
    }

    private fun observeGameProgressOptions() {
        viewModelScope.launch {
            backlogTrackrDBRepository.allGameProgressOptionsFlow.collect { progressOptions ->
                _selectionsState.update { state ->
                    state.copy(
                        availableProgressOptions = progressOptions,
                        // If nothing is selected yet, pick the first available option
                        selectedProgressOption = if (state.selectedProgressOption.isEmpty() && progressOptions.isNotEmpty()) {
                            progressOptions[0].progressName
                        } else {
                            state.selectedProgressOption
                        }
                    )
                }
            }
        }
    }

    fun updateOwnership(owned: Boolean) {
        _selectionsState.update { it.copy(isOwned = owned) }
    }

    fun updatePlatformSelections(platform: String, selected: Boolean) {
        _selectionsState.update { state ->
            val updatedPlatformSelections:Set<String> = if (selected) {
                state.selectedPlatforms + platform
            } else {
                state.selectedPlatforms - platform
            }
            // If PC is not selected, then the pc stores list should be empty
            val pcStores = if (updatedPlatformSelections.contains(DefaultPlatforms.PC.platformName))
                state.selectedStores else emptySet()
            state.copy(selectedPlatforms = updatedPlatformSelections, selectedStores = pcStores)
        }
    }

    fun updatePcStoreSelections(store: String, selected: Boolean) {
        _selectionsState.update { state ->
            val updatedStoreSelections = if (selected) {
                state.selectedStores + store
            } else {
                state.selectedStores - store
            }
            state.copy(selectedStores = updatedStoreSelections)
        }
    }

    fun updateProgressSelection(progress: String) {
        _selectionsState.update { state ->
            state.copy(selectedProgressOption = progress)
        }
    }

    fun addNewGame() {
        viewModelScope.launch {
            val gameSearchVal = _gameDetailState.value.data ?: return@launch

            val userSelections = _selectionsState.value

            val gameEntity: LibraryGameEntity = LibraryGameEntity(
                gameId = gameSearchVal.id.toLong(),
                name = gameSearchVal.name,
                timeToBeat = gameSearchVal.timeToBeat ?: "0h",
                summary = gameSearchVal.summary,
                coverUrl = gameSearchVal.coverURL,
                isOwned = userSelections.isOwned,
                gameProgress = userSelections.selectedProgressOption
            )

            val platformsList = userSelections.selectedPlatforms.toList()
            val storesList = if(userSelections.selectedPlatforms.contains(DefaultPlatforms.PC.platformName)) userSelections.selectedStores.toList() else emptyList()
            if (backlogTrackrDBRepository.checkIfGameExistsInLibrary(gameEntity.gameId)) {
                _gameAddEvent.emit(GameAddEvent(isSuccess = false, message = "Game already exists in library"))
            } else {
                backlogTrackrDBRepository.addLibraryGame(
                    game = gameEntity,
                    platforms = platformsList,
                    stores = storesList
                )
                _gameAddEvent.emit(GameAddEvent(isSuccess = true, message = "Game added to library"))
            }
        }
    }
}

data class UserSelectionsState(
    val availablePlatforms: List<GameAttribute.PlatformModel> = emptyList(),
    val availablePcStores: List<GameAttribute.PCStoreModel> = emptyList(),
    val availableProgressOptions: List<GameAttribute.GameProgressModel> = emptyList(),
    val isOwned: Boolean = true,
    val selectedPlatforms: Set<String> = emptySet(),
    val selectedStores: Set<String> = emptySet(),
    val selectedProgressOption: String = ""
)

data class GameAddEvent (
    val isSuccess: Boolean = false,
    val message: String = ""
)
