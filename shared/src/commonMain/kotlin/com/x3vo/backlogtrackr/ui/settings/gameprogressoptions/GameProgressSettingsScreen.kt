package com.x3vo.backlogtrackr.ui.settings.gameprogressoptions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.domain.model.DefaultGameProgress
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.ui.common.screen.GameAttributesSettingsScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameProgressSettingsScreen(
    handleBackNavigation: () -> Unit,
    viewModel: GameProgressSettingsViewModel = koinViewModel(),
    modifier: Modifier = Modifier
){
    val gameProgressOptions by viewModel.gameProgressOptions.collectAsStateWithLifecycle()
    GameProgressOptionsScreenContent(
        gameProgressOptions = gameProgressOptions,
        handleBackNavigation = handleBackNavigation,
        addGameProgressOptionHandler = { viewModel.addGameProgressOption(it) },
        deleteGameProgressOptionHandler = { viewModel.deleteGameProgressOption(it) },
        resetGameProgressOptionsHandler = { viewModel.resetProgressOptions() },
        modifier = modifier

    )
}

@Composable
private fun GameProgressOptionsScreenContent(
    gameProgressOptions: List<GameAttribute.GameProgressModel>,
    handleBackNavigation: () -> Unit = {},
    addGameProgressOptionHandler: (String) -> Unit = {},
    deleteGameProgressOptionHandler: (String) -> Unit = {},
    resetGameProgressOptionsHandler: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var gameProgressOptionName by rememberSaveable{ mutableStateOf("") }

    GameAttributesSettingsScreen(
        screenTitle = "Modify Progress Options",
        handleBackNavigation = handleBackNavigation,
        userInput = gameProgressOptionName,
        handleUserInputChange = { gameProgressOptionName = it },
        userInputHint = "Add a new game progress option",
        attributesList = gameProgressOptions,
        addAttributeHandler = addGameProgressOptionHandler,
        deleteAttributeHandler = deleteGameProgressOptionHandler,
        resetAttributesHandler = resetGameProgressOptionsHandler
    )

}

@Composable
@PreviewScreenSizes
private fun GameProgressOptionsScreenPreview() {
    GameProgressOptionsScreenContent(
        gameProgressOptions = DefaultGameProgress.entries.map { GameAttribute.GameProgressModel(it.progressName) }
    )
}