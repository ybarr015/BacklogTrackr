package com.x3vo.backlogtrackr.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.x3vo.backlogtrackr.utils.AppDimensions

@Composable
fun HomeScreenCard(
    cardText: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.paddingMedium),
        onClick = onCardClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimensions.paddingMedium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = cardText,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
private fun FakeHomeScreenCard() {
    HomeScreenCard("Home", onCardClick = {})
}

@Preview
@Composable
private fun HomeScreenCardPreview() {
    FakeHomeScreenCard()
}