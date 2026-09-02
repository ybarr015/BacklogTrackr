package com.x3vo.backlogtrackr.ui.game_search.search_games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.cancel_24px
import backlogtrackr.shared.generated.resources.search_24px
import com.x3vo.backlogtrackr.ui.common.components.BasicListUIState
import com.x3vo.backlogtrackr.ui.common.components.GameSearchCard
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.ui.common.screen.StatefulContent
import com.x3vo.backlogtrackr.ui.game_search.GameSearchObject
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameSearchScreen(
    handleBackNavigation: () -> Unit,
    handleGameSelection: (String) -> Unit,
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
        handleGameSelection = handleGameSelection,
        modifier = modifier
    )
}

@Composable
private fun GameSearchScreenContent(
    searchTextFieldText: String = "",
    onSearchTextUpdate: (String) -> Unit = {},
    handleSearchButtonRequest: () -> Unit = {},
    handleCancelTextInput: () -> Unit = {},
    uiState: BasicListUIState<GameSearchObject>,
    handleBackNavigation: () -> Unit = {},
    handleGameSelection: (String)-> Unit = {},
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

    ScreenWithScaffold(
        title = "Game Search",
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
                            Text("Search for a game!")
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
                            onSearch = {
                                handleSearchButtonRequest()
                                focusManager.clearFocus()
                            }
                        )
                    )

                    FilledIconButton(
                        onClick = {
                            handleSearchButtonRequest()
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.search_24px),
                            contentDescription = "Search"
                        )
                    }

                }

                StatefulContent(
                    uiState.isLoading,
                    uiState.error
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(nestedScrollConnection),
                        verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                    ) {
                        items(
                            items = uiState.data,
                            key = { it.id.toString() }
                        ) { game ->
                            GameSearchCard(
                                game.name,
                                game.summary,
                                imageURI = game.coverURL,
                                Modifier.fillMaxWidth()
                                    .clickable(onClick = {
                                        handleGameSelection(game.id.toString())
                                    })
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
private fun GameSearchScreenPreview() {
    GameSearchScreenContent(
        searchTextFieldText = "test",
        uiState = BasicListUIState<GameSearchObject>(
            data = listOf(
                GameSearchObject(
                    id = 212UL,
                    name = "test",
                    coverURL = "",
                    summary = "summary"
                )
            )
        )
    )
}

@PreviewScreenSizes
@Composable
private fun GameSearchScreenErrorPreview() {
    GameSearchScreenContent(
        uiState = BasicListUIState<GameSearchObject>(
            error = "test"
        )
    )
}

@PreviewScreenSizes
@Composable
private fun GameSearchLoadingPreview() {
    GameSearchScreenContent(
        uiState = BasicListUIState<GameSearchObject>(
            isLoading = true
        )
    )
}
