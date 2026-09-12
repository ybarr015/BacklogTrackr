package com.x3vo.backlogtrackr.ui.library_games

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.domain.model.LibraryGameDetailModel
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithBackNav
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LibraryGameDetailScreen(
    gameId: Long,
    handleBackNav: () -> Unit,
    viewModel: LibraryGameDetailViewModel = koinViewModel(
        parameters = { parametersOf(gameId) }
    ),
    modifier: Modifier = Modifier
) {
    val libraryGame: LibraryGameDetailModel by viewModel.libraryGame.collectAsStateWithLifecycle()

    LibraryGameDetailScreenContent(
        libraryGame = libraryGame,
        handleBackNav = handleBackNav,
        modifier = modifier
    )

}

@Composable
private fun LibraryGameDetailScreenContent(
    libraryGame: LibraryGameDetailModel = LibraryGameDetailModel(),
    handleBackNav: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ScreenWithBackNav(
        title = libraryGame.name,
        canNavBack = true,
        onNavigateBack = handleBackNav,
        navButtonEnabled = true,
        modifier = modifier
    ) {

    }

}

@Composable
@Preview
fun LibraryGameDetailScreenPreview() {
    LibraryGameDetailScreenContent(
        libraryGame = LibraryGameDetailModel(name = "test")
    )
}