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
    val route: NavigationScreen,
    val cardName: String
)

@Serializable
sealed interface NavigationScreen {
    @Serializable object HOME : NavigationScreen
    @Serializable object AUTH : NavigationScreen
    @Serializable object GAMESEARCH : NavigationScreen {
        val cardName = "Search"
    }
    @Serializable object PLATFORMS : NavigationScreen

    @Serializable object STORES : NavigationScreen

    @Serializable object GAMEPROGRESSOPTIONS : NavigationScreen

    @Serializable object SETTINGS : NavigationScreen {
        const val cardName = "Settings"
    }

    @Serializable data class GAMESEARCHDETAIL(val gameId:String) : NavigationScreen

    @Serializable object LIBRARYGAMES : NavigationScreen {
        val cardName = "View Library"
    }

    companion object {
        val homeScreenCards = listOf<HomeScreenCard>(
            HomeScreenCard(GAMESEARCH, GAMESEARCH.cardName),
            HomeScreenCard(LIBRARYGAMES, LIBRARYGAMES.cardName),
            HomeScreenCard(SETTINGS, SETTINGS.cardName)
        )

        val navBarRoutes = listOf(LIBRARYGAMES, GAMESEARCH)
    }

}

@Composable
fun BackLogTrackrNavHost (
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController,
        startDestination = NavigationScreen.HOME,
        modifier = modifier) {

        //Dev HomeScreen
        composable<NavigationScreen.HOME> {
            HomeScreen(
                handleCardClick = { navController.navigate(it)},
                cards = NavigationScreen.homeScreenCards
            )
        }

        //GameSearch
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

        //Library
        composable<NavigationScreen.LIBRARYGAMES> {
            LibraryGamesScreen(
                handleNavToSearchScreen = { navController.navigate(NavigationScreen.GAMESEARCH) },
                handleNavToSettingsScreen = { navController.navigate(NavigationScreen.SETTINGS) }
            )
        }

        //settings
        composable<NavigationScreen.SETTINGS> {
            SettingsScreen (
                navigateToAuth = { navController.navigate(NavigationScreen.AUTH) },
                navigateToProgress = { navController.navigate(NavigationScreen.GAMEPROGRESSOPTIONS) },
                navigateToStores = { navController.navigate(NavigationScreen.STORES) },
                navigateToPlatforms = { navController.navigate(NavigationScreen.PLATFORMS) },
                navBackHandler = { navController.navigateUp() }
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
        composable<NavigationScreen.AUTH> {
            AuthScreen(
                handleBackNavigation = { navController.navigateUp() }
            )
        }

    }

}