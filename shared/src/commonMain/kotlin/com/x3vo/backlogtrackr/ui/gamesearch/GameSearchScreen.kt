package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.cancel_24px
import backlogtrackr.shared.generated.resources.search_24px
import com.x3vo.backlogtrackr.navigation.NavigationScreen
import com.x3vo.backlogtrackr.ui.common.components.GameSearchCard
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameSearchScreen(
    handleBackNavigation: () -> Unit,
    viewModel: GameSearchViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchTextFieldText by viewModel.searchKeyword.collectAsStateWithLifecycle()
    GameSearchScreenContent(
        searchTextFieldText = searchTextFieldText,
        onSearchTextUpdate = { viewModel.updateSearchKeyword(it) },
        handleSearchButtonRequest = { viewModel.handleSearchButtonRequest() },
        handleCancelTextInput = { viewModel.updateSearchKeyword("") },
        uiState = uiState,
        handleBackNavigation = handleBackNavigation,
        modifier = modifier
    )
}

@Composable
fun GameSearchScreenContent(
    searchTextFieldText: String,
    onSearchTextUpdate: (String) -> Unit,
    handleSearchButtonRequest: () -> Unit,
    handleCancelTextInput: () -> Unit,
    uiState: GameSearchUIState,
    handleBackNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenWithScaffold(
        title = NavigationScreen.GameSearch.screenName,
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(AppDimensions.paddingSmall)
            ) {

                //text field entry
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchTextFieldText,
                        onValueChange = {
                            onSearchTextUpdate(it)
                        },
                        singleLine = true,
                        label = {
                            Text("Game Name")
                        },
                        modifier = Modifier.weight(1f),
                        trailingIcon = {

                            if (searchTextFieldText.isNotEmpty()) {
                                IconButton(
                                    onClick = handleCancelTextInput
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.cancel_24px),
                                        contentDescription = "cancel text input"
                                    )
                                }
                            }

                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = { handleSearchButtonRequest() }
                        )
                    )

                    FilledIconButton(
                        onClick = { handleSearchButtonRequest() }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.search_24px),
                            contentDescription = "Search"
                        )
                    }

                }

                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (uiState.error != null) {
                    Text(
                        text = uiState.error,
                        color = Color.Red
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                    ) {
                        items(
                            items = uiState.gameData,
                            key = { it.id.toString() }
                        ) { game ->
                            GameSearchCard(
                                game.name,
                                game.summary,
                                imageURI = game.coverURL,
                                Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

            }


        }
    }
}

@PreviewScreenSizes
@Composable
fun GameSearchScreenPreview() {
    GameSearchScreenContent(
        searchTextFieldText = "test",
        onSearchTextUpdate = {},
        handleSearchButtonRequest = {},
        handleCancelTextInput = {},
        uiState = GameSearchUIState(
            gameData = listOf(SimpleGameObject(id = 212UL, name = "test", coverURL = "", summary = "summary"))
        ),
        handleBackNavigation = {}
    )
}

@PreviewScreenSizes
@Composable
fun GameSearchScreenErrorPreview() {
    GameSearchScreenContent(
        searchTextFieldText = "",
        onSearchTextUpdate = {},
        handleSearchButtonRequest = {},
        handleCancelTextInput = {},
        uiState = GameSearchUIState(
            error = "test"
        ),
        handleBackNavigation = {}
    )
}

@PreviewScreenSizes
@Composable
fun GameSearchLoadingPreview() {
    GameSearchScreenContent(
        searchTextFieldText = "",
        onSearchTextUpdate = {},
        handleSearchButtonRequest = {},
        handleCancelTextInput = {},
        uiState = GameSearchUIState(
            isLoading = true
        ),
        handleBackNavigation = {}
    )
}
