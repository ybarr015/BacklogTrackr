package com.x3vo.backlogtrackr.ui.common.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.ic_broken_image
import backlogtrackr.shared.generated.resources.loading_img
import coil3.compose.AsyncImage
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource

import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp

@Composable
fun GameSearchCard(
    gameName: String,
    gameSummary: String,
    imageURI: String,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Card {
                AsyncImage(
                    model = imageURI,
                    contentDescription = "networkImage",
                    modifier = Modifier.size(height = 120.dp, width = 90.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(Res.drawable.ic_broken_image),
                    placeholder = painterResource(Res.drawable.loading_img)
                )
            }

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
}

@Preview
@Composable
fun GameSearchCardPreview(){
    GameSearchCard("gameName","game summary", "")
}