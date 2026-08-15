package com.x3vo.backlogtrackr.ui.common.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScreenWithScaffold(
    title: String,
    canNavBack: Boolean,
    onNavigateBack: () -> Unit = {},
    navButtonEnabled: Boolean,
    modifier: Modifier,
    content: @Composable () -> Unit
    ) {

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            GenericTopAppBar(
                title = title,
                canNavigateBack = canNavBack,
                navigateBack = onNavigateBack,
                navButtonEnabled = navButtonEnabled
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = modifier
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            content()
        }
    }
}

@Composable
fun FakeScreenWithScaffold() {
    ScreenWithScaffold(modifier = Modifier, title = "test", canNavBack = false, navButtonEnabled = true) {
        Text("test")
    }
}

@Preview(device = TABLET)
@Composable
fun ScreenWithScaffoldPreview() {
    FakeScreenWithScaffold()
}