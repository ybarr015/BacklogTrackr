package com.x3vo.backlogtrackr.ui.library_games

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.search_24px
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgress
import com.x3vo.backlogtrackr.domain.model.DefaultPCStores
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.ui.common.components.LibraryGameCard
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithOptionsAndSettings
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LibraryGamesScreen(
    handleNavToSearchScreen: () -> Unit = {},
    handleNavToSettingsScreen: () -> Unit = {},
    viewModel: LibraryGamesViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val searchKeyword by viewModel.searchKeyword.collectAsStateWithLifecycle()
    val libraryGames by viewModel.filteredLibraryGames.collectAsStateWithLifecycle()
    val filterUiState by viewModel.filterUiState.collectAsStateWithLifecycle()
    LibraryGamesScreenContent(
        searchKeyword = searchKeyword,
        onSearchKeywordUpdate = { viewModel.updateSearchKeyword(it) },
        libraryGames = libraryGames,
        filterUiState = filterUiState,
        handlePlatformFilterSelection = { viewModel.onPlatformFilterSelected(filterName = it) },
        handleProgressFilterSelection = { viewModel.onProgressFilterSelected(filterName = it) },
        handlePCStoreFilterSelection = { viewModel.onPcStoreFilterSelected(filterName = it)},
        handleNavToSearchScreen = handleNavToSearchScreen,
        handleNavToSettingsScreen = handleNavToSettingsScreen,
        modifier = modifier
    )
}

@Composable
private fun LibraryGamesScreenContent(
    searchKeyword: String = "",
    onSearchKeywordUpdate: (String) -> Unit = {},
    libraryGames: List<LibraryGameDetailModel>,
    filterUiState: FilterUiState = FilterUiState(),
    handlePlatformFilterSelection: (String) -> Unit = {},
    handleProgressFilterSelection: (String) -> Unit = {},
    handlePCStoreFilterSelection: (String) -> Unit = {},
    handleNavToSearchScreen: () -> Unit = {},
    handleNavToSettingsScreen: ()-> Unit = {},
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    var filtersAreShowing by rememberSaveable{ mutableStateOf(false) }

    val isFilterActive = filterUiState.selectedPlatformFilters.isNotEmpty() ||
            filterUiState.selectedProgressStateFilters.isNotEmpty() ||
            filterUiState.selectedPcStoreFilters.isNotEmpty()

    ScreenWithOptionsAndSettings(
        title = "Your Games",
        isFilterActive = isFilterActive,
        onFilterButtonClicked = { filtersAreShowing = !filtersAreShowing },
        onSettingsButtonClicked = handleNavToSettingsScreen,
        modifier = modifier
    ) {

        if (filterUiState.availablePlatforms.isEmpty() || filterUiState.availableProgressStates.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
            ) {
                LibraryGamesFilters(
                    filtersAreVisible = filtersAreShowing,
                    availablePlatforms = filterUiState.availablePlatforms,
                    availablePcStores = filterUiState.availablePcStores,
                    availableProgressStates = filterUiState.availableProgressStates,
                    selectedPlatformFilters = filterUiState.selectedPlatformFilters,
                    selectedPcStoreFilters = filterUiState.selectedPcStoreFilters,
                    selectedProgressFilters = filterUiState.selectedProgressStateFilters,
                    handlePlatformFilterSelection = handlePlatformFilterSelection,
                    handleProgressFilterSelection = handleProgressFilterSelection,
                    handlePCStoreFilterSelection = handlePCStoreFilterSelection
                )

                TextField(
                    value = searchKeyword,
                    onValueChange = onSearchKeywordUpdate,
                    singleLine = true,
                    label = {
                        Text("Search your library games")
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = AppDimensions.paddingSmall),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                        }
                    )
                )

                if (libraryGames.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = handleNavToSearchScreen
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
                            ) {
                                Icon(painter = painterResource(Res.drawable.search_24px), contentDescription = "Add Game")
                                Text("Add a game")
                            }
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                        horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                        columns = GridCells.Adaptive(125.dp),
                        contentPadding = PaddingValues(AppDimensions.paddingSmall)
                    ) {

                        items(items = libraryGames, key = {
                            it.gameId
                        }) { game ->
                            LibraryGameCard(
                                coverURL = game.coverUrl,
                                name = game.name
                            )
                        }

                    }
                }
            }
        }




    }

}

@Composable
@PreviewScreenSizes
private fun LibraryGamesScreenPreview() {
    LibraryGamesScreenContent(
        libraryGames = listOf(
            LibraryGameDetailModel(
                gameId = 0,
                name = "gameName",
                timeToBeat = "1h",
                summary = "gameSummary",
                isOwned = true,
                coverUrl = "",
                progressOption = GameAttribute.GameProgressModel(
                    progressName = "Started"
                ),
                platforms = DefaultPlatforms.entries.map { GameAttribute.PlatformModel(it.platformName) },
                pcStores = DefaultPCStores.entries.map { GameAttribute.PCStoreModel(it.storeName) }
            )
        ),
        filterUiState = FilterUiState(
            availablePlatforms = DefaultPlatforms.entries.map { GameAttribute.PlatformModel(it.platformName) },
            availablePcStores = DefaultPCStores.entries.map { GameAttribute.PCStoreModel(it.storeName) },
            availableProgressStates = DefaultGameProgress.entries.map { GameAttribute.GameProgressModel(it.progressName) },
            selectedPlatformFilters = setOf(DefaultPlatforms.PC.platformName)
        )
    )
}