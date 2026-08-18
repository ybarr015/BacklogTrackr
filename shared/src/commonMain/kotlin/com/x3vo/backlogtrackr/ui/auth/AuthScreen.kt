package com.x3vo.backlogtrackr.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x3vo.backlogtrackr.navigation.NavigationScreen
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    handleBackNavigation: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel()
) {
    val authToken by viewModel.authToken.collectAsStateWithLifecycle()

    AuthScreenContent(
        authToken = authToken ?: "No token found",
        onAuthenticate = { viewModel.authenticate("test-api-token") },
        onClearAuth = { viewModel.clearAuth() },
        handleBackNavigation = handleBackNavigation,
        modifier = modifier
    )
}

@Composable
fun AuthScreenContent(
    authToken: String,
    onAuthenticate: () -> Unit,
    onClearAuth: () -> Unit,
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
                text = "Your api token key is: $authToken"
            )

            Button(
                onClick = onAuthenticate,
                modifier = Modifier.width(200.dp)
            ) {
                Text("Authenticate with api")
            }

            Button(
                onClick = onClearAuth,
                modifier = Modifier.width(200.dp)
            ) {
                Text("Clear Auth Storage")
            }
        }


    }
}

@PreviewScreenSizes
@Composable
fun AuthScreenPreview() {
    AuthScreenContent(
        authToken = "preview-token",
        onAuthenticate = {},
        onClearAuth = {},
        handleBackNavigation = {}
    )
}
