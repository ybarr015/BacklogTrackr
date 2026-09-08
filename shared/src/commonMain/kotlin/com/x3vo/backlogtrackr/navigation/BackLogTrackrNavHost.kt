package com.x3vo.backlogtrackr.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
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

        val navBarRoutes: List<NavigationScreen> = listOf(LIBRARYGAMES, GAMESEARCH)
    }

}

@Composable
fun BackLogTrackrNavHost (
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val handleNavBarNavigation: (NavigationScreen) -> Unit = remember(navController) {
        { destination ->
            val currentDestination = navController.currentDestination
            val isResumed = navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
            
            // Only navigate if we're not already there and the current screen is active
            if (isResumed && currentDestination?.hasRoute(destination::class) == false) {
                navController.navigate(destination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavigationScreen.LIBRARYGAMES,
        modifier = modifier,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(400)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(400)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(400)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(400)) }
    ) {

        //Dev HomeScreen
        composable<NavigationScreen.HOME> {
            HomeScreen(
                handleCardClick = { navController.navigate(it)},
                cards = NavigationScreen.homeScreenCards
            )
        }

        //GameSearch
        composable<NavigationScreen.GAMESEARCH>(
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {
            GameSearchScreen(
                handleGameSelection = { gameId ->
                    navController.navigate(NavigationScreen.GAMESEARCHDETAIL(gameId = gameId))
                },
                handleNavBarNavigation = handleNavBarNavigation
            )
        }
        composable<NavigationScreen.GAMESEARCHDETAIL> { backstackEntry ->
            val route: NavigationScreen.GAMESEARCHDETAIL = backstackEntry.toRoute()
            GameSearchDetailScreen(
                gameId = route.gameId,
                handleBackNavigation = { navController.navigateUp() },
                onNavigateToLibrary = {
                    handleNavBarNavigation(NavigationScreen.LIBRARYGAMES)
                }
            )
        }

        //Library
        composable<NavigationScreen.LIBRARYGAMES>(
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {
            LibraryGamesScreen(
                handleNavToSearchScreen = { handleNavBarNavigation(NavigationScreen.GAMESEARCH) },
                handleNavToSettingsScreen = { navController.navigate(NavigationScreen.SETTINGS) },
                handleNavBarNavigation = handleNavBarNavigation
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