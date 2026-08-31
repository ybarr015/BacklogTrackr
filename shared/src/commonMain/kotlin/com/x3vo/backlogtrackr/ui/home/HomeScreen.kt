package com.x3vo.backlogtrackr.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.x3vo.backlogtrackr.navigation.HomeScreenCard
import com.x3vo.backlogtrackr.navigation.NavigationScreen
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions

@Composable
fun HomeScreen(
    handleCardClick: (Any) -> Unit,
    cards: List<HomeScreenCard>,
    modifier: Modifier = Modifier
) {
    HomeScreenContent(
        handleCardClick = handleCardClick,
        cards = cards,
        modifier = modifier
    )
}

@Composable
private fun HomeScreenContent(
    handleCardClick: (Any) -> Unit = {},
    cards: List<HomeScreenCard>,
    modifier: Modifier = Modifier,
) {

    ScreenWithScaffold(
        title = "Home",
        canNavBack = false,
        navButtonEnabled = false,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingMedium),
            contentPadding = PaddingValues(AppDimensions.paddingSmall)
        ) {
            items(
                items = cards,
                key = { item  ->
                    item.cardName
                }
            ) { item ->
                HomeScreenCard(
                    item.cardName,
                    onCardClick = { handleCardClick(item.route) },
                    modifier = Modifier.width(150.dp)
                )
            }
        }
    }

}

@Composable
private fun FakeHomeScreenContent() {
    HomeScreenContent(
        cards = NavigationScreen.homeScreenCards
    )
}

@PreviewScreenSizes
@Composable
private fun HomeScreenContentPreview() {
    FakeHomeScreenContent()
}