package com.x3vo.backlogtrackr.ui.library_games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.utils.AppDimensions

@Composable
fun LibraryGamesFilters(
    availablePlatforms: List<GameAttribute.PlatformModel> = listOf(),
    availablePcStores: List<GameAttribute.PCStoreModel> = listOf(),
    availableProgressStates: List<GameAttribute.GameProgressModel> = listOf(),
    selectedPlatformFilters: Set<String> = setOf<String>(),
    selectedPcStoreFilters: Set<String> = setOf<String>(),
    selectedProgressFilters: Set<String> = setOf<String>(),
    handlePlatformFilterSelection: (String) -> Unit = {},
    handlePCStoreFilterSelection: (String) -> Unit = {},
    handleProgressFilterSelection: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {


    Column(modifier = modifier) {
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

            availablePlatforms.forEach { platform ->
                item(key = platform.platformName) {
                    FilterChip(
                        selected = selectedPlatformFilters.contains(platform.platformName),
                        onClick = {
                            handlePlatformFilterSelection(platform.platformName)
                        },
                        label = {
                            Text(text = platform.platformName)
                        },
                        modifier = Modifier.padding(end = AppDimensions.paddingSmall)
                    )
                }

                val pcStoresAvailable =
                    platform.platformName == DefaultPlatforms.PC.platformName && availablePcStores.isNotEmpty()

                if (pcStoresAvailable) {

                    val isPcSelected = selectedPlatformFilters.contains(
                        DefaultPlatforms.PC.platformName
                    )

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
                        items = availablePcStores,
                        key = { pcStore -> "store.${pcStore.pcStoreName}" }
                    ) { pcStore ->
                        AnimatedVisibility(
                            visible = isPcSelected,
                            enter = expandHorizontally(expandFrom = Alignment.Start) + fadeIn(),
                            exit = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()
                        ) {
                            FilterChip(
                                selected = selectedPcStoreFilters.contains(pcStore.pcStoreName),
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
                items = availableProgressStates,
                key = { progress ->
                    progress.progressName
                }
            ) { progress ->
                FilterChip(
                    selected = selectedProgressFilters.contains(progress.progressName),
                    onClick = {
                        handleProgressFilterSelection(progress.progressName)
                    },
                    label = {
                        Text(text = progress.progressName)
                    }
                )
            }

        }

    }

}