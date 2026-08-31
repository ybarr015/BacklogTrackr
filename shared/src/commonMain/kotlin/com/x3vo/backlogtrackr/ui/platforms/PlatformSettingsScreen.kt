package com.x3vo.backlogtrackr.ui.platforms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.add_24px
import com.x3vo.backlogtrackr.room.platforms.PlatformEntity
import com.x3vo.backlogtrackr.ui.common.components.BasicListUIState
import com.x3vo.backlogtrackr.ui.common.components.GameAttributeItem
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlatformSettingsScreen(
    handleBackNavigation: () -> Unit,
    viewModel: PlatformViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlatformSettingsScreenContent(
        uiState = uiState,
        addPlatformHandler = { viewModel.addPlatform(it) },
        handleBackNavigation = handleBackNavigation,
        deletePlatformHandler = { viewModel.deletePlatform(it) },
        resetPlatformsHandler = { viewModel.resetPlatforms() },
        modifier = modifier
    )


}

@Composable
private fun PlatformSettingsScreenContent(
    uiState: BasicListUIState<PlatformEntity>,
    handleBackNavigation: () -> Unit = {},
    addPlatformHandler: (String) -> Unit = {},
    deletePlatformHandler: (String) -> Unit = {},
    resetPlatformsHandler: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if(source == NestedScrollSource.UserInput) {
                    focusManager.clearFocus()
                }
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if(listState.isScrollInProgress) {
            focusManager.clearFocus()
        }
    }

    var newPlatformName by rememberSaveable { mutableStateOf("") }

    ScreenWithScaffold(
        title = "Manage Platforms",
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimensions.paddingSmall),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = newPlatformName,
                    onValueChange = { newPlatformName = it },
                    label = { Text("Add Platform") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {
                        addPlatformHandler(newPlatformName)
                        newPlatformName = ""
                        focusManager.clearFocus()
                    })
                )
                FilledIconButton(onClick = {
                    addPlatformHandler(newPlatformName)
                    newPlatformName = ""
                    focusManager.clearFocus()
                }) {
                    Icon(painter = painterResource(Res.drawable.add_24px), contentDescription = "Add")
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
                    .nestedScroll(nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.data) { platform ->
                    GameAttributeItem(
                        attributeText = platform.platformName,
                        onDelete = { deletePlatformHandler(platform.platformName) }
                    )
                    HorizontalDivider()
                }

                item {
                    Button(
                        onClick = { resetPlatformsHandler() },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text(text = "Reset to Default Platforms")
                    }
                }
            }

        }
    }
}



@Composable
@PreviewScreenSizes
private fun PlatformSettingsScreenPreview() {
    PlatformSettingsScreenContent(
        uiState = BasicListUIState<PlatformEntity>(data = listOf(PlatformEntity(id = 0, platformName = "test")))
    )
}
