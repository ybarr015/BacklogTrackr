package com.x3vo.backlogtrackr.ui.game_search.add_game

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgressSet
import com.x3vo.backlogtrackr.domain.model.DefaultPCStoresSet
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.DefaultPlatformsSet
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.GameSearchObject
import com.x3vo.backlogtrackr.domain.model.toPlatformModel
import com.x3vo.backlogtrackr.ui.common.components.BasicDataUiState
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithBackNav
import com.x3vo.backlogtrackr.ui.common.screen.StatefulContent
import com.x3vo.backlogtrackr.utils.AppDimensions
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GameSearchDetailScreen(
    gameId:String,
    handleBackNavigation: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    viewModel: GameSearchDetailViewModel = koinViewModel(
        parameters = { parametersOf(gameId) }
    ),
    modifier: Modifier = Modifier
) {
    val gameDetailsState by viewModel.gameDetailState.collectAsStateWithLifecycle()
    val userSelectionsState by viewModel.selectionsState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.gameAddEvent.collectLatest { event ->
            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = "Go to Library"
            )
            if (result == SnackbarResult.ActionPerformed) {
                onNavigateToLibrary()
            }
        }
    }

    GameSearchDetailContent(
        gameDetailsState = gameDetailsState,
        userSelectionsState = userSelectionsState,
        onOwnershipUpdate = viewModel::updateOwnership,
        onPlatformSelection = viewModel::updatePlatformSelections,
        onPcStoreSelection = viewModel::updatePcStoreSelections,
        onProgressSelection = viewModel::updateProgressSelection,
        handleBackNavigation = handleBackNavigation,
        addGameHandler = viewModel::addNewGame,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameSearchDetailContent(
    gameDetailsState: BasicDataUiState<GameSearchObject>,
    userSelectionsState: UserSelectionsState,
    onOwnershipUpdate: (Boolean) -> Unit = {},
    onPlatformSelection: (GameAttribute.PlatformModel, Boolean) -> Unit = { _, _ -> },
    onPcStoreSelection: (GameAttribute.PCStoreModel, Boolean) -> Unit = { _, _ -> },
    onProgressSelection: (GameAttribute.GameProgressModel) -> Unit = {},
    handleBackNavigation: () -> Unit = {},
    addGameHandler: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    ScreenWithBackNav(
        title = gameDetailsState.data?.name ?: "Game Details",
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        snackBarHostState = snackbarHostState,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(AppDimensions.paddingSmall)
            ) {

                StatefulContent(
                    gameDetailsState.isLoading,
                    gameDetailsState.error
                ) {
                    val gameData = gameDetailsState.data

                    if (gameData == null) {
                        Text(
                            text = "No Game Data was found from the api server",
                            color = Color.Red
                        )
                    } else {
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
                                selected = userSelectionsState.isOwned,
                                onClick = { onOwnershipUpdate(true) },
                                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                            ) {
                                Text("Own it")
                            }
                            SegmentedButton(
                                selected = !userSelectionsState.isOwned,
                                onClick = { onOwnershipUpdate(false) },
                                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                            ) {
                                Text("Wishlist")
                            }
                        }

                        if(userSelectionsState.isOwned) {
                            if (userSelectionsState.availablePlatforms.isNotEmpty()) {
                                Text(
                                    text = "What Platforms do you own it on?",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    userSelectionsState.availablePlatforms.forEach { platform ->
                                        val isSelected = userSelectionsState.selectedPlatforms.contains(platform)
                                        FilterChip(
                                            selected = isSelected,
                                            onClick = { onPlatformSelection(platform, !isSelected) },
                                            label = { Text(platform.platformName) }
                                        )
                                    }
                                }
                            }

                            if (userSelectionsState.selectedPlatforms.contains(DefaultPlatforms.PC.toPlatformModel()) && userSelectionsState.availablePcStores.isNotEmpty()) {
                                Text(
                                    text = "Which PC stores?",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(start = 16.dp)
                                ) {
                                    userSelectionsState.availablePcStores.forEach { store ->
                                        val isSelected = userSelectionsState.selectedStores.contains(store)
                                        FilterChip(
                                            selected = isSelected,
                                            onClick = { onPcStoreSelection(store, !isSelected) },
                                            label = { Text(store.pcStoreName) }
                                        )
                                    }
                                }
                            }

                            if (userSelectionsState.availableProgressOptions.isNotEmpty()) {
                                Text(
                                    text = "How far are you into it?",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = it }
                                ) {
                                    TextField(
                                        value = userSelectionsState.selectedProgressOption.progressName,
                                        onValueChange = {},
                                        readOnly = true,
                                        enabled = false,
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                        modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true).fillMaxWidth(),
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                            disabledIndicatorColor = MaterialTheme.colorScheme.onSurface,
                                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        userSelectionsState.availableProgressOptions.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption.progressName) },
                                                onClick = {
                                                    onProgressSelection(selectionOption)
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }


                        }

                        Button(onClick = addGameHandler) {
                            Text("Add Game")
                        }
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
        gameDetailsState = BasicDataUiState<GameSearchObject>(
            data = GameSearchObject(
                id = 212UL,
                name = "test",
                coverURL = "",
                summary = "summary",
                timeToBeat = "10h"
            )
        ),
        userSelectionsState = UserSelectionsState(
            availablePlatforms = DefaultPlatformsSet,
            availablePcStores = DefaultPCStoresSet,
            availableProgressOptions = DefaultGameProgressSet,
            selectedPlatforms = setOf(DefaultPlatforms.PC.toPlatformModel()),
            isOwned = true
        )
    )
}
