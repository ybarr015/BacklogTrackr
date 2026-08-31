package com.x3vo.backlogtrackr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.x3vo.backlogtrackr.ui.auth.AuthScreen
import com.x3vo.backlogtrackr.ui.gameprogressoptions.GameProgressSettingsScreen
import com.x3vo.backlogtrackr.ui.gamesearch.GameSearchDetailScreen
import com.x3vo.backlogtrackr.ui.gamesearch.GameSearchScreen
import com.x3vo.backlogtrackr.ui.home.HomeScreen
import com.x3vo.backlogtrackr.ui.platforms.PlatformSettingsScreen
import com.x3vo.backlogtrackr.ui.stores.StoresSettingsScreen
import kotlinx.serialization.Serializable

data class HomeScreenCard (
    val route: Any,
    val cardName: String
)

@Serializable
object NavigationScreen {
    @Serializable object HOME{
        val cardName = "Home"
    }
    @Serializable object AUTH {
        val cardName = "Auth"
    }
    @Serializable object GAMESEARCH {
        val cardName = "Search"
    }
    @Serializable object PLATFORMS {
        val cardName = "Platform Options"
    }

    @Serializable object STORES {
        val cardName = "Store Options"
    }

    @Serializable object GAMEPROGRESSOPTIONS {
        val cardName = "Game Progress Options"
    }

    @Serializable data class GAMESEARCHDETAIL(val gameId:String)

    val homeScreenCards = listOf<HomeScreenCard>(
        HomeScreenCard(AUTH, AUTH.cardName),
        HomeScreenCard(GAMESEARCH, GAMESEARCH.cardName),
        HomeScreenCard(PLATFORMS, PLATFORMS.cardName),
        HomeScreenCard(STORES, STORES.cardName),
        HomeScreenCard(GAMEPROGRESSOPTIONS, GAMEPROGRESSOPTIONS.cardName)
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
                handleBackNavigation = { navController.navigateUp() }
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

    }

}