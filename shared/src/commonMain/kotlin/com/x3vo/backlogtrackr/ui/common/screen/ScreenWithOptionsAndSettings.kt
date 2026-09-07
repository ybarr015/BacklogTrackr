package com.x3vo.backlogtrackr.ui.common.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.x3vo.backlogtrackr.ui.common.components.TopAppBarWithFiltersAndSettings

@Composable
fun ScreenWithOptionsAndSettings(
    title: String,
    onFilterButtonClicked: () -> Unit = {},
    onSettingsButtonClicked: () -> Unit = {},
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ScreenWithScaffold(
        topBar = {
            TopAppBarWithFiltersAndSettings(
                title = title,
                onFilterButtonClick = onFilterButtonClicked,
                onSettingsButtonClick = onSettingsButtonClicked
            )
        },
        snackBarHostState = snackBarHostState,
        modifier = modifier
    ) {
        content()
    }

}

@Composable
private fun FakeScreen() {
    ScreenWithOptionsAndSettings(
        title = "test"
    ) {
        Text("Test")
    }
}

@Composable
@Preview
private fun ScreenWithOptionsAndSettingsPrev() {
    FakeScreen()
}