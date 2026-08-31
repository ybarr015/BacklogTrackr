package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.ic_broken_image
import backlogtrackr.shared.generated.resources.loading_img
import coil3.compose.AsyncImage
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GameSearchDetailScreen(
    gameId:String,
    handleBackNavigation: () -> Unit,
    viewModel: GameSearchDetailViewModel = koinViewModel(
        parameters = { parametersOf(gameId) }
    ),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameSearchDetailContent(
        uiState = uiState,
        onOwnershipUpdate = viewModel::updateOwnership,
        onPlatformUpdate = viewModel::updatePlatform,
        onPcStoreUpdate = viewModel::updatePcStore,
        onProgressUpdate = viewModel::updateProgress,
        handleBackNavigation = handleBackNavigation,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameSearchDetailContent(
    uiState: GameSearchDetailUIState,
    onOwnershipUpdate: (Boolean) -> Unit,
    onPlatformUpdate: (String, Boolean) -> Unit,
    onPcStoreUpdate: (String, Boolean) -> Unit,
    onProgressUpdate: (String) -> Unit,
    handleBackNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressOptions = listOf("Not Started", "In Progress", "Finished", "Dropped")
    var expanded by rememberSaveable { mutableStateOf(false) }

    ScreenWithScaffold(
        title = uiState.gameData.firstOrNull()?.name ?: "Game Details",
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(AppDimensions.paddingSmall)
            ) {

                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.error != null) {
                    Text(
                        text = uiState.error,
                        color = Color.Red
                    )
                } else {
                    val gameData = uiState.gameData.first()

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = gameData.coverURL,
                            contentDescription = "networkImage",
                            modifier = Modifier.size(height = 200.dp, width = 150.dp),
                            contentScale = ContentScale.Crop,
                            error = painterResource(Res.drawable.ic_broken_image),
                            placeholder = painterResource(Res.drawable.loading_img)
                        )

                        Column(Modifier.padding(AppDimensions.paddingSmall)) {
                            Text(
                                text = gameData.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Time to beat: ${gameData.timeToBeat}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = gameData.summary,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Text(
                        text = "Do you own this game or add to wish list?",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SegmentedButton(
                            selected = uiState.isOwned,
                            onClick = { onOwnershipUpdate(true) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Text("Own it")
                        }
                        SegmentedButton(
                            selected = !uiState.isOwned,
                            onClick = { onOwnershipUpdate(false) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                        ) {
                            Text("Wishlist")
                        }
                    }

                    if(uiState.isOwned) {
                        Text(
                            text = "What Platforms do you own it on?",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        if (uiState.availablePlatforms.isNotEmpty()) {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                uiState.availablePlatforms.forEach { platform ->
                                    val isSelected = uiState.selectedPlatforms.contains(platform)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { onPlatformUpdate(platform, !isSelected) },
                                        label = { Text(platform) }
                                    )
                                }
                            }
                        } else {
                            Text (
                                text = "No Available Platforms Defined, please define some"
                            )
                        }

                        if (uiState.selectedPlatforms.contains("PC") && uiState.availablePcStores.isNotEmpty()) {
                            Text(
                                text = "Which PC stores?",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                uiState.availablePcStores.forEach { store ->
                                    val isSelected = uiState.selectedStores.contains(store)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { onPcStoreUpdate(store, !isSelected) },
                                        label = { Text(store) }
                                    )
                                }
                            }
                        }

                        Text(
                            text = "How far are you into it?",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = it }
                        ) {
                            TextField(
                                value = uiState.progress,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true).fillMaxWidth(),
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                progressOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            onProgressUpdate(selectionOption)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Button(onClick = {}) {
                        Text("Add Game")
                    }


                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun GameSearchDetailScreenPreview() {
    GameSearchDetailContent(
        uiState = GameSearchDetailUIState(
            gameData = listOf(DetailGameObject(id = 212UL, name = "test", coverURL = "", summary = "summary", timeToBeat = "10h")),
            availablePlatforms = listOf("PC", "PS5", "Xbox", "Switch"),
            availablePcStores = listOf("Steam", "Epic"),
            selectedPlatforms = setOf("PC")
        ),
        onOwnershipUpdate = {},
        onPlatformUpdate = { _, _ -> },
        onPcStoreUpdate = { _, _ -> },
        onProgressUpdate = {},
        handleBackNavigation = {}
    )
}
