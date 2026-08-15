package com.x3vo.backlogtrackr.ui.common.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import backlogtrackr.shared.generated.resources.Back
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.arrow_back_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun GenericTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    canNavigateBack: Boolean = false,
    navButtonEnabled: Boolean = true,
    navigateBack: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    enabled = navButtonEnabled,
                    onClick = navigateBack
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.arrow_back_24px),
                        contentDescription = stringResource(Res.string.Back)
                    )
                }
            }
        }

    )
}

@Composable
fun FakeTopAppBar() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                GenericTopAppBar(
                    title = "top app bar text",
                    canNavigateBack = true,
                    navigateBack = {}
                )
            }
        ) { padding ->
            Text("test", modifier = Modifier.padding(top = padding.calculateTopPadding()))
        }
    }
}

@Preview()
@Composable
fun GenericTopAppBarPreview() {
    FakeTopAppBar()

}