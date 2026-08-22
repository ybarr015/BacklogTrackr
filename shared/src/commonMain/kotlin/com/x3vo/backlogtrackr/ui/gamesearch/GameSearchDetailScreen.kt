package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameSearchDetailScreen(
    gameId:String,
    handleBackNavigation: () -> Unit,
    viewModel: GameSearchDetailViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameSearchDetailContent(
        uiState = uiState,
        handleBackNavigation = handleBackNavigation,
        modifier = modifier
    )
}

@Composable
private fun GameSearchDetailContent(
    uiState: GameSearchDetailUIState,
    handleBackNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {

    ScreenWithScaffold(
        title = "Game Details",
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

                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (uiState.error != null) {
                    Text(
                        text = uiState.error,
                        color = Color.Red
                    )
                } else {

                }

            }


        }
    }
}

@PreviewScreenSizes
@Composable
private fun GameSearchDetailScreenPreview() {
    GameSearchDetailContent(
        uiState = GameSearchDetailUIState(
            gameData = listOf(SimpleGameObject(id = 212UL, name = "test", coverURL = "", summary = "summary"))
        ),
        handleBackNavigation = {}
    )
}
