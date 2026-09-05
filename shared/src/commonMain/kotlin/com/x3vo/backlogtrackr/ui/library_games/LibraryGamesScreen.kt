package com.x3vo.backlogtrackr.ui.library_games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgress
import com.x3vo.backlogtrackr.domain.model.DefaultPCStores
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.room.LibraryGame.GameWithDetails
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformEntity
import com.x3vo.backlogtrackr.ui.common.components.LibraryGameCard
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LibraryGamesScreen(
    handleBackNavigation: () -> Unit,
    viewModel: LibraryGamesViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val libraryGames by viewModel.filteredLibraryGames.collectAsStateWithLifecycle()
    val filterUiState by viewModel.filterUiState.collectAsStateWithLifecycle()
    LibraryGamesScreenContent(
        libraryGames = libraryGames,
        filterUiState = filterUiState,
        handlePlatformFilterSelection = { viewModel.onPlatformFilterSelected(filterName = it) },
        handleProgressFilterSelection = { viewModel.onProgressFilterSelected(filterName = it) },
        handlePCStoreFilterSelection = { viewModel.onPcStoreFilterSelected(filterName = it)},
        handleBackNavigation = handleBackNavigation,
        modifier = modifier
    )
}

@Composable
private fun LibraryGamesScreenContent(
    libraryGames: List<GameWithDetails>,
    filterUiState: FilterUiState = FilterUiState(),
    handlePlatformFilterSelection: (String) -> Unit = {},
    handleProgressFilterSelection: (String) -> Unit = {},
    handlePCStoreFilterSelection: (String) -> Unit = {},
    handleBackNavigation: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    ScreenWithScaffold(
        title = "Your Games",
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
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
                modifier = Modifier.fillMaxSize()
            ) {

                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(horizontal = AppDimensions.paddingSmall),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Text(
                            text = "Platforms",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(end = AppDimensions.paddingSmall)
                        )
                    }

                    filterUiState.availablePlatforms.forEach { platform ->
                        item(key = platform.platformName) {
                            FilterChip(
                                selected = filterUiState.selectedPlatformFilters.contains(platform.platformName),
                                onClick = {
                                    handlePlatformFilterSelection(platform.platformName)
                                },
                                label = {
                                    Text(text = platform.platformName)
                                },
                                modifier = Modifier.padding(end = AppDimensions.paddingSmall)
                            )
                        }

                        val pcStoresAvailable = platform.platformName == DefaultPlatforms.PC.platformName && filterUiState.availablePcStores.isNotEmpty()

                        if (pcStoresAvailable) {

                            val isPcSelected = filterUiState.selectedPlatformFilters.contains(
                                DefaultPlatforms.PC.platformName)

                            item(key = "PCDividerStart") {
                                AnimatedVisibility(
                                    visible = isPcSelected,
                                    enter = expandHorizontally(expandFrom = Alignment.Start) + fadeIn(),
                                    exit = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()
                                ) {
                                    VerticalDivider(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .padding(end = 8.dp),
                                        thickness = 1.dp
                                    )
                                }
                            }

                            items(
                                items = filterUiState.availablePcStores,
                                key = { pcStore -> "store.${pcStore.pcStoreName}" }
                            ) { pcStore ->
                                AnimatedVisibility(
                                    visible = isPcSelected,
                                    enter = expandHorizontally(expandFrom = Alignment.Start) + fadeIn(),
                                    exit = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()
                                ) {
                                    FilterChip(
                                        selected = filterUiState.selectedPcStoreFilters.contains(pcStore.pcStoreName),
                                        onClick = {
                                            handlePCStoreFilterSelection(pcStore.pcStoreName)
                                        },
                                        label = {
                                            Text(text = pcStore.pcStoreName)
                                        },
                                        modifier = Modifier.padding(end = AppDimensions.paddingSmall)

                                    )
                                }
                            }

                            item(key = "PCDividerEnd") {
                                AnimatedVisibility(
                                    visible = isPcSelected,
                                    enter = expandHorizontally(expandFrom = Alignment.Start) + fadeIn(),
                                    exit = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()
                                ) {
                                    VerticalDivider(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .padding(end = 8.dp),
                                        thickness = 1.dp
                                    )
                                }
                            }
                        }

                    }

                }
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                    contentPadding = PaddingValues(horizontal = AppDimensions.paddingSmall),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Text(
                            text = "Progress",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    items(
                        items = filterUiState.availableProgressStates,
                        key = { progress ->
                            progress.progressName
                        }
                    ) { progress ->
                        FilterChip(
                            selected = filterUiState.selectedProgressStateFilters.contains(progress.progressName),
                            onClick = {
                                handleProgressFilterSelection(progress.progressName)
                            },
                            label = {
                                Text(text = progress.progressName)
                            }
                        )
                    }

                }
                LazyVerticalGrid(
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                    columns = GridCells.Adaptive(125.dp),
                    contentPadding = PaddingValues(AppDimensions.paddingSmall)
                ) {

                    items(items = libraryGames, key = {
                        it.gameData.gameId
                    }) { game ->
                        LibraryGameCard(
                            coverURL = game.gameData.coverUrl,
                            name = game.gameData.name
                        )
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
            GameWithDetails(
                gameData = LibraryGameEntity(
                    gameId = 0,
                    name = "gameName",
                    timeToBeat = "1h",
                    summary = "gameSummary",
                    isOwned = true,
                    gameProgress = "Started",
                    coverUrl = ""
                ),
                progressOption = GameProgressOptionEntity(
                    progressOption = "Started"
                ),
                platforms = DefaultPlatforms.entries.map { PlatformEntity(it.platformName) },
                pcStores = DefaultPCStores.entries.map { PCStoreEntity(it.storeName) }
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