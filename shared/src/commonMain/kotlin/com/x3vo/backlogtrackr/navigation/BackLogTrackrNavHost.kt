package com.x3vo.backlogtrackr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import backlogtrackr.shared.generated.resources.Home
import backlogtrackr.shared.generated.resources.Res
import com.x3vo.backlogtrackr.ui.home.HomeScreen
import org.jetbrains.compose.resources.StringResource

enum class NavigationScreen(val title: StringResource) {
    Home(title = Res.string.Home)
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
                cards = NavigationScreen.entries
            )
        }

    }

}