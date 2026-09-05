package com.x3vo.backlogtrackr.ui.settings.platforms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.domain.model.DefaultPlatforms
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.ui.common.screen.GameAttributesSettingsScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlatformSettingsScreen(
    handleBackNavigation: () -> Unit,
    viewModel: PlatformViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val platforms by viewModel.platforms.collectAsStateWithLifecycle()

    PlatformSettingsScreenContent(
        platforms = platforms,
        addPlatformHandler = { viewModel.addPlatform(it) },
        handleBackNavigation = handleBackNavigation,
        deletePlatformHandler = { viewModel.deletePlatform(it) },
        resetPlatformsHandler = { viewModel.resetPlatforms() },
        modifier = modifier
    )


}

@Composable
private fun PlatformSettingsScreenContent(
    platforms: List<GameAttribute.PlatformModel>,
    handleBackNavigation: () -> Unit = {},
    addPlatformHandler: (String) -> Unit = {},
    deletePlatformHandler: (String) -> Unit = {},
    resetPlatformsHandler: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    var newPlatformName by rememberSaveable { mutableStateOf("") }

    GameAttributesSettingsScreen(
        screenTitle = "Modify Platforms",
        handleBackNavigation = handleBackNavigation,
        userInput = newPlatformName,
        handleUserInputChange = { newPlatformName = it },
        userInputHint = "Add Platform",
        attributesList = platforms,
        addAttributeHandler = addPlatformHandler,
        deleteAttributeHandler = deletePlatformHandler,
        resetAttributesHandler = resetPlatformsHandler
    )
}



@Composable
@PreviewScreenSizes
private fun PlatformSettingsScreenPreview() {
    PlatformSettingsScreenContent(
        platforms = DefaultPlatforms.entries.map { GameAttribute.PlatformModel(it.platformName) }
    )
}
