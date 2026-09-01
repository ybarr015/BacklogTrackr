package com.x3vo.backlogtrackr.ui.librarygames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.x3vo.backlogtrackr.room.LibraryGame.GameWithDetails
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformEntity
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
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

        Column(
            modifier = modifier.fillMaxSize().padding(AppDimensions.paddingSmall),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)) {

                items(items = libraryGames, key = {
                    it.game.gameId
                } ) { game ->
                    Card {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
                            modifier = Modifier.padding(AppDimensions.paddingSmall)
                        ) {
                            Text(
                                text = game.game.name
                            )
                            Text(
                                text = game.game.summary
                            )

                        }

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
                    gameId = 0, name = "gameName", timeToBeat = "1h", summary = "gameSummary", isOwned = true, gameProgress = "Started"
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