package com.x3vo.backlogtrackr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.x3vo.backlogtrackr.ui.settings.auth.AuthScreen
import com.x3vo.backlogtrackr.ui.settings.gameprogressoptions.GameProgressSettingsScreen
import com.x3vo.backlogtrackr.ui.game_search.add_game.GameSearchDetailScreen
import com.x3vo.backlogtrackr.ui.game_search.search_games.GameSearchScreen
import com.x3vo.backlogtrackr.ui.home.HomeScreen
import com.x3vo.backlogtrackr.ui.library_games.LibraryGamesScreen
import com.x3vo.backlogtrackr.ui.settings.platforms.PlatformSettingsScreen
import com.x3vo.backlogtrackr.ui.settings.SettingsScreen
import com.x3vo.backlogtrackr.ui.settings.stores.StoresSettingsScreen
import kotlinx.serialization.Serializable

data class HomeScreenCard (
    val route: Any,
    val cardName: String
)

@Serializable
object NavigationScreen {
    @Serializable object HOME
    @Serializable object AUTH
    @Serializable object GAMESEARCH {
        val cardName = "Search"
    }
    @Serializable object PLATFORMS

    @Serializable object STORES

    @Serializable object GAMEPROGRESSOPTIONS

    @Serializable object SETTINGS {
        const val cardName = "Settings"
    }

    @Serializable data class GAMESEARCHDETAIL(val gameId:String)

    @Serializable object LIBRARYGAMES {
        val cardName = "View Library"
    }

    val homeScreenCards = listOf<HomeScreenCard>(
        HomeScreenCard(GAMESEARCH, GAMESEARCH.cardName),
        HomeScreenCard(LIBRARYGAMES, LIBRARYGAMES.cardName),
        HomeScreenCard(SETTINGS, SETTINGS.cardName)
    )
}

@Composable
fun BackLogTrackrNavHost (
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController,
        startDestination = NavigationScreen.HOME,
        modifier = modifier) {

        composable<NavigationScreen.HOME> {
            HomeScreen(
                handleCardClick = { navController.navigate(it)},
                cards = NavigationScreen.homeScreenCards
            )
        }

        composable<NavigationScreen.AUTH> {
            AuthScreen(
                handleBackNavigation = { navController.navigateUp() }
            )
        }
        composable<NavigationScreen.GAMESEARCH> {
            GameSearchScreen(
                handleBackNavigation = { navController.navigateUp() },
                handleGameSelection = { gameId ->
                    navController.navigate(NavigationScreen.GAMESEARCHDETAIL(gameId = gameId))
                }
            )
        }
        composable<NavigationScreen.GAMESEARCHDETAIL> { backstackEntry ->
            val route: NavigationScreen.GAMESEARCHDETAIL = backstackEntry.toRoute()
            GameSearchDetailScreen(
                gameId = route.gameId,
                handleBackNavigation = { navController.navigateUp() },
                onNavigateToLibrary = {
                    navController.navigate(NavigationScreen.LIBRARYGAMES) {
                        popUpTo(NavigationScreen.HOME)
                    }
                }
            )
        }
        composable<NavigationScreen.PLATFORMS> {
            PlatformSettingsScreen(
                handleBackNavigation = { navController.navigateUp() }
            )
        }
        composable<NavigationScreen.STORES> {
            StoresSettingsScreen(
                handleBackNavigation = { navController.navigateUp() }
            )
        }
        composable<NavigationScreen.GAMEPROGRESSOPTIONS> {
            GameProgressSettingsScreen(
                handleBackNavigation = { navController.navigateUp() }
            )
        }
        composable<NavigationScreen.LIBRARYGAMES> {
            LibraryGamesScreen(
                handleBackNavigation = { navController.navigateUp() },
                handleNavToSearchScreen = { navController.navigate(NavigationScreen.GAMESEARCH) }
            )
        }
        composable<NavigationScreen.SETTINGS> {
            SettingsScreen (
                navigateToAuth = { navController.navigate(NavigationScreen.AUTH) },
                navigateToProgress = { navController.navigate(NavigationScreen.GAMEPROGRESSOPTIONS) },
                navigateToStores = { navController.navigate(NavigationScreen.STORES) },
                navigateToPlatforms = { navController.navigate(NavigationScreen.PLATFORMS) },
                navBackHandler = { navController.navigateUp() }
            )
        }

    }

}