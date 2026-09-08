package com.x3vo.backlogtrackr.ui.common.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScreenWithBackNav(
    title: String,
    canNavBack: Boolean,
    onNavigateBack: () -> Unit = {},
    navButtonEnabled: Boolean,
    bottomBar: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable () -> Unit
    ) {
    ScreenWithScaffold(
        topBar = {
            TopAppBarWithBackNav(
                title = title,
                canNavigateBack = canNavBack,
                navigateBack = onNavigateBack,
                navButtonEnabled = navButtonEnabled
            )
        },
        bottomBar = bottomBar,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
private fun FakeScreen() {
    ScreenWithBackNav(modifier = Modifier, title = "test", canNavBack = true, navButtonEnabled = true) {
        Text("test")
    }
}

@Preview(device = TABLET)
@Composable
private fun ScreenWithBackNavPreview() {
    FakeScreen()
}