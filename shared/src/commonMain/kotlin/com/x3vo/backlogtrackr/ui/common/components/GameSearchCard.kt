package com.x3vo.backlogtrackr.ui.common.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.x3vo.backlogtrackr.utils.AppDimensions

@Composable
fun GameSearchCard(
    gameName: String,
    gameSummary: String,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier) {
        Column(Modifier.padding(AppDimensions.paddingSmall)) {
            Text(
                text = gameName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = gameSummary,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun GameSearchCardPreview(){
    GameSearchCard("gameName", "summary")
}