package com.x3vo.backlogtrackr.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.x3vo.backlogtrackr.ui.common.components.NavigationBarCustom
import com.x3vo.backlogtrackr.ui.settings.auth.AuthScreen
import com.x3vo.backlogtrackr.ui.settings.gameprogressoptions.GameProgressSettingsScreen
import com.x3vo.backlogtrackr.ui.game_search.add_game.GameSearchDetailScreen
import com.x3vo.backlogtrackr.ui.game_search.search_games.GameSearchScreen
import com.x3vo.backlogtrackr.ui.home.HomeScreen
import com.x3vo.backlogtrackr.ui.library_games.LibraryGameDetailScreen
import com.x3vo.backlogtrackr.ui.library_games.LibraryGamesScreen
import com.x3vo.backlogtrackr.ui.settings.platforms.PlatformSettingsScreen
import com.x3vo.backlogtrackr.ui.settings.SettingsScreen
import com.x3vo.backlogtrackr.ui.settings.stores.StoresSettingsScreen
import kotlinx.serialization.Serializable

data class HomeScreenCard(
    val route: NavigationScreen,
    val cardName: String
)

@Serializable
sealed interface NavigationScreen {
    @Serializable
    object HOME : NavigationScreen
    @Serializable
    object AUTH : NavigationScreen
    @Serializable
    object GAMESEARCH : NavigationScreen {
        val cardName = "Search"
    }

    @Serializable
    object PLATFORMS : NavigationScreen

    @Serializable
    object STORES : NavigationScreen

    @Serializable
    object GAMEPROGRESSOPTIONS : NavigationScreen

    @Serializable
    object SETTINGS : NavigationScreen {
        const val cardName = "Settings"
    }

    @Serializable
    data class GAMESEARCHDETAIL(val gameId: String) : NavigationScreen

    @Serializable
    object LIBRARYGAMES : NavigationScreen {
        val cardName = "View Library"
    }

    @Serializable
    data class LIBRARY_GAME_DETAIL(val gameId: Long): NavigationScreen

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
fun BackLogTrackrNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showNavBar = NavigationScreen.navBarRoutes.any { route ->
        currentDestination?.hasRoute(route::class) == true
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showNavBar) {
                val selected = NavigationScreen.navBarRoutes.find { route ->
                    currentDestination?.hasRoute(route::class) == true
                } ?: NavigationScreen.LIBRARYGAMES

                NavigationBarCustom(
                    currentDestination = selected,
                    handleNavBarNavigation = { handleNavBarNavigation(it, navController) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationScreen.LIBRARYGAMES,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(400)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(400)
                )
            }
        ) {

            //Dev HomeScreen
            composable<NavigationScreen.HOME> {
                HomeScreen(
                    handleCardClick = { handleNavigation(it, navController) },
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
                        handleNavigation(NavigationScreen.GAMESEARCHDETAIL(gameId = gameId), navController)
                    }
                )
            }
            composable<NavigationScreen.GAMESEARCHDETAIL> { backstackEntry ->
                val route: NavigationScreen.GAMESEARCHDETAIL = backstackEntry.toRoute()
                GameSearchDetailScreen(
                    gameId = route.gameId,
                    handleBackNavigation = { navController.navigateUp() },
                    onNavigateToLibrary = {
                        handleNavBarNavigation(NavigationScreen.LIBRARYGAMES, navController)
                    }
                )
            }

            //Library
            composable<NavigationScreen.LIBRARYGAMES>(
                enterTransition = { fadeIn(tween(400)) },
                exitTransition = { fadeOut(tween(400)) }
            ) {
                LibraryGamesScreen(
                    handleNavToSearchScreen = { handleNavBarNavigation(NavigationScreen.GAMESEARCH, navController) },
                    handleNavToSettingsScreen = {
                        handleNavigation(NavigationScreen.SETTINGS, navController)
                    },
                    handleNavToLibraryGameDetailScreen = {
                        handleNavigation(NavigationScreen.LIBRARY_GAME_DETAIL(it), navController)
                    }
                )
            }

            composable<NavigationScreen.LIBRARY_GAME_DETAIL> { backstackEntry ->
                val route: NavigationScreen.LIBRARY_GAME_DETAIL = backstackEntry.toRoute()
                LibraryGameDetailScreen(
                    gameId = route.gameId,
                    handleBackNav = { navController.navigateUp() },

                )
            }

            //settings
            composable<NavigationScreen.SETTINGS> {
                SettingsScreen(
                    navigateToAuth = { handleNavigation(NavigationScreen.AUTH,navController) },
                    navigateToProgress = { handleNavigation(NavigationScreen.GAMEPROGRESSOPTIONS,navController) },
                    navigateToStores = { handleNavigation(NavigationScreen.STORES,navController) },
                    navigateToPlatforms = { handleNavigation(NavigationScreen.PLATFORMS,navController) },
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
}

fun handleNavBarNavigation(destination: NavigationScreen, navController: NavHostController) {

    if (checkIfNotAlreadyNavigated(destination, navController)) {
        navController.navigate(destination) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun handleNavigation(screen: NavigationScreen, navController: NavHostController) {
    if (checkIfNotAlreadyNavigated(screen, navController)) {
        navController.navigate(screen)
    }

}

fun checkIfNotAlreadyNavigated(destination: NavigationScreen, navController: NavHostController): Boolean {
    val currentDestination: NavDestination? = navController.currentDestination
    val isResumed =
        navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
    return isResumed && currentDestination?.hasRoute(destination::class) == false
}