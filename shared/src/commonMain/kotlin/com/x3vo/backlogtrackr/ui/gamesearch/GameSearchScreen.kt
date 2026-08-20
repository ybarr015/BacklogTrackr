package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.navigation.NavigationScreen
import com.x3vo.backlogtrackr.ui.common.components.GameSearchCard
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameSearchScreen(
    handleBackNavigation: () -> Unit,
    viewModel: GameSearchViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameSearchScreenContent(
        uiState = uiState,
        handleBackNavigation = handleBackNavigation,
        modifier = modifier
    )
}

@Composable
fun GameSearchScreenContent(
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
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(AppDimensions.paddingSmall),
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                ) {
                    items(
                        items = uiState.gameData,
                        key = { it.id.toString() }
                    ) { game ->
                        GameSearchCard(
                            game.name,
                            game.summary
                        )
                    }
                }
            }
        }
    }
}
