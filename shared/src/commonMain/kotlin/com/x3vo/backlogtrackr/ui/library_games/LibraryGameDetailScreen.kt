package com.x3vo.backlogtrackr.ui.library_games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.ic_broken_image
import backlogtrackr.shared.generated.resources.loading_img
import coil3.compose.AsyncImage
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgress
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgressSet
import com.x3vo.backlogtrackr.domain.model.DefaultPCStores
import com.x3vo.backlogtrackr.domain.model.DefaultPCStoresSet
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.DefaultPlatformsSet
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.domain.model.toGameProgressModel
import com.x3vo.backlogtrackr.domain.model.toPcStoreModel
import com.x3vo.backlogtrackr.domain.model.toPlatformModel
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithBackNav
import com.x3vo.backlogtrackr.utils.AppDimensions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.time.Instant

@Composable
fun LibraryGameDetailScreen(
    gameId: Long,
    handleBackNav: () -> Unit,
    viewModel: LibraryGameDetailViewModel = koinViewModel(
        parameters = { parametersOf(gameId) }
    ),
    modifier: Modifier = Modifier
) {
    val libraryGame: LibraryGameDetailModel by viewModel.libraryGame.collectAsStateWithLifecycle()
    val gameOptions: LibraryGameDetailOptions by viewModel.libraryGameOptions.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.editGameEvent.collectLatest { event ->
            snackbarHostState.showSnackbar(
                message = event
            )
        }
    }

    LibraryGameDetailScreenContent(
        libraryGame = libraryGame,
        libraryGameOptions = gameOptions,
        onOwnershipUpdate = viewModel::updateOwnershipSelection,
        onPlatformSelection = viewModel::updatePlatformSelection,
        onPcStoreSelection = viewModel::updatePcStoreSelection,
        onProgressSelection = viewModel::updateProgressSelection,
        onGameDelete = {
            viewModel.deleteGame(it)
            handleBackNav()
        },
        onGameEdit = viewModel::updateGameDetails,
        handleBackNav = handleBackNav,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryGameDetailScreenContent(
    libraryGame: LibraryGameDetailModel = LibraryGameDetailModel(),
    libraryGameOptions: LibraryGameDetailOptions = LibraryGameDetailOptions(),
    onOwnershipUpdate: (Boolean) -> Unit = {},
    onPlatformSelection: (GameAttribute.PlatformModel, Boolean) -> Unit = { _, _ -> },
    onPcStoreSelection: (GameAttribute.PCStoreModel, Boolean) -> Unit = { _, _ -> },
    onProgressSelection: (GameAttribute.GameProgressModel) -> Unit = {},
    onGameDelete: (Long) -> Unit = {},
    onGameEdit: () -> Unit = {},
    handleBackNav: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ScreenWithBackNav(
        title = libraryGame.name,
        canNavBack = true,
        onNavigateBack = handleBackNav,
        navButtonEnabled = true,
        snackBarHostState = snackbarHostState,
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimensions.paddingSmall)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = libraryGame.coverUrl,
                        contentDescription = "network image",
                        modifier = Modifier.size(height = 200.dp, width = 150.dp),
                        contentScale = ContentScale.Crop,
                        error = painterResource(Res.drawable.ic_broken_image),
                        placeholder = painterResource(Res.drawable.loading_img)
                    )

                    val timeInstant = Instant.fromEpochMilliseconds(libraryGame.addedAt)
                    val localTime =
                        timeInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date

                    Column(Modifier.padding(AppDimensions.paddingSmall)) {
                        Text(
                            text = libraryGame.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Added on: ${localTime.month.number}-${localTime.day}-${localTime.year}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Time to beat: ${libraryGame.timeToBeat}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = libraryGame.summary,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
            }

            item {
                Column {
                    Text(
                        text = "Ownership Status",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SegmentedButton(
                            selected = libraryGameOptions.isOwned,
                            onClick = { onOwnershipUpdate(true) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Text("Own it")
                        }
                        SegmentedButton(
                            selected = !libraryGameOptions.isOwned,
                            onClick = { onOwnershipUpdate(false) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                        ) {
                            Text("Wishlist")
                        }
                    }
                }
            }

            if (libraryGameOptions.isOwned) {
                if (libraryGameOptions.availablePlatforms.isNotEmpty()) {
                    item {
                        Text(
                            text = "Platforms owned on:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            libraryGameOptions.availablePlatforms.forEach { platform ->
                                val isSelected =
                                    libraryGameOptions.selectedPlatforms.contains(platform)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onPlatformSelection(platform, !isSelected) },
                                    label = { Text(platform.platformName) }
                                )
                            }
                        }
                    }
                }

                if (libraryGameOptions.availablePcStores.isNotEmpty() && libraryGameOptions.selectedPlatforms.contains(
                        DefaultPlatforms.PC.toPlatformModel())) {
                    item {
                        Text(
                            text = "PC stores owned on:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            libraryGameOptions.availablePcStores.forEach { store ->
                                val isSelected = libraryGameOptions.selectedStores.contains(store)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onPcStoreSelection(store, !isSelected) },
                                    label = { Text(store.pcStoreName) }
                                )
                            }
                        }
                    }
                }


                item {
                    Text(
                        text = "Game Progress:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it }
                    ) {
                        TextField(
                            value = libraryGameOptions.selectedProgressOption.progressName,
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                true
                            ).fillMaxWidth(),
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
                            libraryGameOptions.availableProgressOptions.forEach { selectionOption ->
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

            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = { onGameEdit() }
                    ) {
                        Text("Save Changes")
                    }
                    Spacer(
                        modifier = Modifier.width(AppDimensions.paddingSmall)
                    )
                    Button(
                        onClick = { onGameDelete(libraryGame.gameId) }
                    ) {
                        Text("Delete Game")
                    }

                }
            }
        }
    }
}

@Composable
@Preview
fun LibraryGameDetailScreenPreview() {
    LibraryGameDetailScreenContent(
        libraryGame = LibraryGameDetailModel(
            gameId = 0L,
            name = "GameName",
            coverUrl = "",
            timeToBeat = "10h",
            summary = "test summary",
            isOwned = true,
            progressOption = DefaultGameProgress.NOT_STARTED.toGameProgressModel(),
            platforms = setOf(DefaultPlatforms.PC.toPlatformModel()),
            pcStores = setOf(DefaultPCStores.STEAM.toPcStoreModel()),
            addedAt = 1789186669L
        ),
        libraryGameOptions = LibraryGameDetailOptions(
            availablePlatforms = DefaultPlatformsSet,
            availablePcStores = DefaultPCStoresSet,
            availableProgressOptions = DefaultGameProgressSet,
            isOwned = true,
            selectedPlatforms = setOf(DefaultPlatforms.PC.toPlatformModel()),
            selectedStores = setOf(DefaultPCStores.STEAM.toPcStoreModel()),
            selectedProgressOption = DefaultGameProgress.IN_PROGRESS.toGameProgressModel()
        )
    )
}