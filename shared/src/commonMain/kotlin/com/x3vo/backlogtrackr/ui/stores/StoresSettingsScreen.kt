package com.x3vo.backlogtrackr.ui.stores

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
import com.x3vo.backlogtrackr.room.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.ui.common.components.BasicListUIState
import com.x3vo.backlogtrackr.ui.common.components.GameAttributeItem
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun StoresSettingsScreen(
    handleBackNavigation: () -> Unit,
    viewModel: StoresSettingsViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    StoresSettingsScreenContent(
        uiState = uiState,
        handleBackNavigation = handleBackNavigation,
        addStoreHandler = {
            viewModel.addStore(it)
        },
        deleteStoreHandler = {
            viewModel.deleteStore(it)
        },
        resetStoresHandler = {
            viewModel.resetStores()
        }

    )
}

@Composable
private fun StoresSettingsScreenContent(
    uiState: BasicListUIState<PCStoreEntity>,
    handleBackNavigation: () -> Unit = {},
    addStoreHandler: (String) -> Unit = {},
    deleteStoreHandler: (String) -> Unit = {},
    resetStoresHandler: () -> Unit = {},
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

    var newStoreName by rememberSaveable { mutableStateOf("") }

    ScreenWithScaffold(
        title = "Manage Pc Stores",
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
                    value = newStoreName,
                    onValueChange = { newStoreName = it },
                    label = { Text("Add PC Store") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {
                        addStoreHandler(newStoreName)
                        newStoreName = ""
                        focusManager.clearFocus()
                    })
                )
                FilledIconButton(onClick = {
                    addStoreHandler(newStoreName)
                    newStoreName = ""
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
                items(uiState.data) { store->
                    GameAttributeItem(
                        attributeText = store.pcStoreName,
                        onDelete = { deleteStoreHandler(store.pcStoreName) }
                    )
                    HorizontalDivider()
                }

                item {
                    Button(
                        onClick = { resetStoresHandler() },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text(text = "Reset to Default Stores")
                    }
                }
            }

        }
    }
}

@Composable
@PreviewScreenSizes
private fun StoresSettingsScreenPreview(){
    StoresSettingsScreenContent(
        uiState = BasicListUIState(
            data = listOf(PCStoreEntity(id = 0, pcStoreName = "test"))
        )
    )
}