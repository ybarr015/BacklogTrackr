package com.x3vo.backlogtrackr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.x3vo.backlogtrackr.ui.auth.AuthScreen
import com.x3vo.backlogtrackr.ui.gamesearch.GameSearchScreen
import com.x3vo.backlogtrackr.ui.home.HomeScreen

enum class NavigationScreen(val screenName: String, val cardName: String) {
    Home(screenName = "Home", cardName = "Home"),
    Auth(screenName = "Authentication", cardName = "Auth"),
    GameSearch(screenName = "GameSearch", cardName = "Search")
}

@Composable
fun BackLogTrackrNavHost (
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController,
        startDestination = NavigationScreen.Home.name,
        modifier = modifier) {

        composable(route = NavigationScreen.Home.name) {
            HomeScreen(
                canNavBack = false,
                handleCardClick = { navController.navigate(it.name)},
                cards = NavigationScreen.entries.filter {
                    it != NavigationScreen.Home
                }
            )
        }

        composable(route = NavigationScreen.Auth.name) {
            AuthScreen(
                handleBackNavigation = { navController.navigateUp() }
            )
        }
        composable(route = NavigationScreen.GameSearch.name) {
            GameSearchScreen(
                handleBackNavigation = { navController.navigateUp() }
            )
        }

    }

}