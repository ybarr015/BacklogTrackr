package com.x3vo.backlogtrackr.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
        onAuthenticate = { viewModel.handleAuthenticationRequest() },
        onFakeAuthenticationRequest = { viewModel.storeFakeAuthenticationData("test-api-token") },
        onClearAuth = { viewModel.clearAuth() },
        handleBackNavigation = handleBackNavigation,
        modifier = modifier
    )
}

@Composable
private fun AuthScreenContent(
    authToken: String = "",
    onAuthenticate: () -> Unit = {},
    onFakeAuthenticationRequest: () -> Unit = {},
    onClearAuth: () -> Unit = {},
    handleBackNavigation: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ScreenWithScaffold(
        title = "Authentication",
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(AppDimensions.paddingSmall)
        ) {

            Text(
                text = "Your api token key is: $authToken"
            )

            Button(
                onClick = onAuthenticate,
                modifier = Modifier.width(300.dp)
            ) {
                Text("Authenticate with api")
            }

            Button(
                onClick = onFakeAuthenticationRequest,
                modifier = Modifier.width(300.dp)
            ) {
                Text("Store fake authentication data")
            }

            Button(
                onClick = onClearAuth,
                modifier = Modifier.width(300.dp)
            ) {
                Text("Clear Auth Storage")
            }
        }


    }
}

@PreviewScreenSizes
@Composable
private fun AuthScreenPreview() {
    AuthScreenContent(
        authToken = "preview-token"
    )
}
