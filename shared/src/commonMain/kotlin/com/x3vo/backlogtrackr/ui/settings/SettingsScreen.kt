package com.x3vo.backlogtrackr.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.arrow_forward_ios_24px
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithBackNav
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource

@PreviewScreenSizes
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateToAuth: () -> Unit = {},
    navigateToProgress: () -> Unit = {},
    navigateToPlatforms: () -> Unit = {},
    navigateToStores: () -> Unit = {},
    navBackHandler: () -> Unit = {},
) {

    ScreenWithBackNav(
        title = "Settings",
        canNavBack = true,
        onNavigateBack = navBackHandler,
        navButtonEnabled = true,
        modifier = modifier
    ) {

        LazyColumn(
            contentPadding = PaddingValues(AppDimensions.paddingSmall),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
        ) {
            item {
                //auth
                SettingsRow(
                    settingName = "Auth",
                    settingText = "Manage api token storage for igdb api",
                    onClickHandler = navigateToAuth
                )
            }

            item {
                //platforms
                SettingsRow(
                    settingName = "Platforms",
                    settingText = "Manage which platforms are available for a game",
                    onClickHandler = navigateToPlatforms
                )
            }

            item {
                //progress
                SettingsRow(
                    settingName = "Progress Options",
                    settingText = "Manage which progress states are available for a game",
                    onClickHandler = navigateToProgress
                )
            }

            item {
                //stores
                SettingsRow(
                    settingName = "PC Stores",
                    settingText = "Manage which pc stores are available for pc platform",
                    onClickHandler = navigateToStores
                )
            }

        }




    }

}

@Preview
@Composable
fun SettingsRow(
    settingName: String = "Setting",
    settingText: String = "Setting Text",
    onClickHandler: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(
            onClick = onClickHandler
        )
    ) {
        Row(
            modifier = Modifier
                .padding(AppDimensions.paddingSmall)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Column(
                modifier.weight(1f)
            ) {
                Text(
                    text = settingName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = settingText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                painter = painterResource(Res.drawable.arrow_forward_ios_24px),
                contentDescription = "Go to $settingName"
            )
        }
    }

}