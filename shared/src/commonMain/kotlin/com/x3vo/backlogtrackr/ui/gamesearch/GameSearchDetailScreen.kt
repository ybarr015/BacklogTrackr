package com.x3vo.backlogtrackr.ui.gamesearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.ic_broken_image
import backlogtrackr.shared.generated.resources.loading_img
import coil3.compose.AsyncImage
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GameSearchDetailScreen(
    gameId:String,
    handleBackNavigation: () -> Unit,
    viewModel: GameSearchDetailViewModel = koinViewModel(
        parameters = { parametersOf(gameId) }
    ),
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
        title = uiState.gameData.firstOrNull()?.name ?: "Game Details",
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
                    val gameData = uiState.gameData.first()

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = gameData.coverURL,
                            contentDescription = "networkImage",
                            modifier = Modifier.size(height = 200.dp, width = 150.dp),
                            contentScale = ContentScale.Crop,
                            error = painterResource(Res.drawable.ic_broken_image),
                            placeholder = painterResource(Res.drawable.loading_img)
                        )

                        Column(Modifier.padding(AppDimensions.paddingSmall)) {
                            Text(
                                text = gameData.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Time to beat: ${gameData.timeToBeat}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = gameData.summary,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
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
private fun GameSearchDetailScreenPreview() {
    GameSearchDetailContent(
        uiState = GameSearchDetailUIState(
            gameData = listOf(DetailGameObject(id = 212UL, name = "test", coverURL = "", summary = "summary", timeToBeat = "10h"))
        ),
        handleBackNavigation = {}
    )
}
