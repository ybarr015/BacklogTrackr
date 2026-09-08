package com.x3vo.backlogtrackr.ui.common.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.search_24px
import backlogtrackr.shared.generated.resources.sports_esports_24px
import com.x3vo.backlogtrackr.navigation.NavigationScreen
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavigationBarCustom(
    currentDestination: NavigationScreen,
    handleNavBarNavigation: (NavigationScreen) -> Unit
) {
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavigationScreen.navBarRoutes.forEach { destination ->
            NavigationBarItem(
                selected = destination == currentDestination,
                onClick = { handleNavBarNavigation(destination) },
                icon = {
                    val icon = if (destination == NavigationScreen.LIBRARYGAMES) {
                        Res.drawable.sports_esports_24px
                    } else {
                        Res.drawable.search_24px
                    }
                    Icon(painter = painterResource(icon), contentDescription = null)
                },
                label = {
                    var navBarText = ""
                    if(destination == NavigationScreen.LIBRARYGAMES){
                        navBarText = "Library"
                    } else if(destination == NavigationScreen.GAMESEARCH) {
                        navBarText = "Add Games"
                    }
                    Text(navBarText)
                }
            )
        }

    }
}