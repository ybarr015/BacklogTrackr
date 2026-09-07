package com.x3vo.backlogtrackr.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.settings_24px
import backlogtrackr.shared.generated.resources.tune_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopAppBarWithFiltersAndSettings(
    title: String = "Screen",
    onFilterButtonClick: () -> Unit = {},
    onSettingsButtonClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(64.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onFilterButtonClick ){
                Icon(
                    painter = painterResource(Res.drawable.tune_24px),
                    contentDescription = "Settings"
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(onClick = onSettingsButtonClick ){
                Icon(
                    painter = painterResource(Res.drawable.settings_24px),
                    contentDescription = "Settings"
                )
            }
        }
    }
}

@Composable
@Preview
private fun TopAppBarWithFiltersAndSettingsPrev() {
    TopAppBarWithFiltersAndSettings()
}