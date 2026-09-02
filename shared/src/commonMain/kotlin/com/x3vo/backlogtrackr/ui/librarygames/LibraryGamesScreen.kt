package com.x3vo.backlogtrackr.ui.librarygames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.ic_broken_image
import backlogtrackr.shared.generated.resources.loading_img
import coil3.compose.AsyncImage
import com.x3vo.backlogtrackr.room.LibraryGame.GameWithDetails
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformEntity
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LibraryGamesScreen(
    handleBackNavigation: () -> Unit,
    viewModel: LibraryGamesViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val libraryGames by viewModel.libraryGames.collectAsStateWithLifecycle()
    LibraryGamesScreenContent(libraryGames = libraryGames, handleBackNavigation, modifier)
}

@Composable
private fun LibraryGamesScreenContent(
    libraryGames: List<GameWithDetails>,
    handleBackNavigation: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    ScreenWithScaffold(
        title = "Your Games",
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        modifier = modifier
    ) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
            columns = GridCells.Adaptive(125.dp),
            contentPadding = PaddingValues(AppDimensions.paddingSmall)
        ) {

            items(items = libraryGames, key = {
                it.game.gameId
            } ) { game ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppDimensions.paddingSmall),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        AsyncImage(
                            model = game.game.coverUrl,
                            contentDescription = "networkImage",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.75f)
                                .clip(RoundedCornerShape(AppDimensions.paddingSmall)),
                            contentScale = ContentScale.Crop,
                            error = painterResource(Res.drawable.ic_broken_image),
                            placeholder = painterResource(Res.drawable.loading_img)
                        )
                        Text(
                            text = game.game.name,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }

    }

}

@Composable
@PreviewScreenSizes
private fun LibraryGamesScreenPreview() {
    LibraryGamesScreenContent(
        libraryGames = listOf(
            GameWithDetails(
                game = LibraryGameEntity(
                    gameId = 0, name = "gameName", timeToBeat = "1h", summary = "gameSummary", isOwned = true, gameProgress = "Started", coverUrl = ""
                ),
                progressOption = GameProgressOptionEntity(
                    attributeId = 0L, progressOption = "Started"
                ),
                platforms = listOf(PlatformEntity(
                    attributeId = 0L, platformName = "PC"
                )),
                pcStores = listOf(PCStoreEntity(
                    attributeId = 0L, pcStoreName = "Steam"
                ))
            )
        )
    )
}