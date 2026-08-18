package com.x3vo.backlogtrackr.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.x3vo.backlogtrackr.navigation.NavigationScreen
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions

@Composable
fun AuthScreen(handleBackNavigation: () -> Unit, modifier: Modifier = Modifier) {
    AuthScreenContent(handleBackNavigation, modifier)
}

@Composable
fun AuthScreenContent(
    handleBackNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenWithScaffold(
        title = NavigationScreen.Auth.screenName,
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
        ) {

            Text(
                text = "Your api token key is: "
            )

            Button(
                onClick = {

                }
            ) {
                Text("Authenticate with api")
            }
        }


    }
}

@PreviewScreenSizes
@Composable
fun AuthScreenPreview() {
    AuthScreenContent(
        {}
    )
}