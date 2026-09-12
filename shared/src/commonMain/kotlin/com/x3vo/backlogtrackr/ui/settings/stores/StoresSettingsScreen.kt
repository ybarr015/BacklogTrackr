package com.x3vo.backlogtrackr.ui.settings.stores

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.domain.model.DefaultPCStoresSet
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.ui.common.screen.GameAttributesSettingsScreen
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun StoresSettingsScreen(
    handleBackNavigation: () -> Unit,
    viewModel: StoresSettingsViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val stores by viewModel.stores.collectAsStateWithLifecycle()
    StoresSettingsScreenContent(
        stores = stores,
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
    stores: Set<GameAttribute.PCStoreModel>,
    handleBackNavigation: () -> Unit = {},
    addStoreHandler: (String) -> Unit = {},
    deleteStoreHandler: (String) -> Unit = {},
    resetStoresHandler: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    var newStoreName by rememberSaveable { mutableStateOf("") }

    GameAttributesSettingsScreen(
        screenTitle = "Modify PC Stores",
        handleBackNavigation = handleBackNavigation,
        userInput = newStoreName,
        handleUserInputChange = { newStoreName = it },
        userInputHint = "Add Pc Store",
        attributesList = stores,
        addAttributeHandler = addStoreHandler,
        deleteAttributeHandler = deleteStoreHandler,
        resetAttributesHandler = resetStoresHandler
    )
}

@Composable
@PreviewScreenSizes
private fun StoresSettingsScreenPreview(){
    StoresSettingsScreenContent(
        stores = DefaultPCStoresSet
    )
}