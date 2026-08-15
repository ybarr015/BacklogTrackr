package com.x3vo.backlogtrackr

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.x3vo.backlogtrackr.navigation.BackLogTrackrNavHost

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    BackLogTrackrNavHost(navController = navController)
}