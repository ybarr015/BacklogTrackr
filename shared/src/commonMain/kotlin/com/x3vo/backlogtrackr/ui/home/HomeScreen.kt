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
import com.x3vo.backlogtrackr.navigation.NavigationScreen
import com.x3vo.backlogtrackr.ui.common.screen.ScreenWithScaffold
import com.x3vo.backlogtrackr.utils.AppDimensions

@Composable
fun HomeScreen(
    canNavBack: Boolean,
    handleCardClick: (NavigationScreen) -> Unit,
    cards: List<NavigationScreen>,
    modifier: Modifier = Modifier
) {
    HomeScreenContent(
        canNavBack = canNavBack,
        handleCardClick = handleCardClick,
        cards = cards,
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    canNavBack: Boolean,
    handleCardClick: (NavigationScreen) -> Unit,
    cards: List<NavigationScreen>,
    modifier: Modifier = Modifier,
) {

    ScreenWithScaffold(
        title = NavigationScreen.Home.screenName,
        canNavBack = canNavBack,
        navButtonEnabled = canNavBack,
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
                key = { item ->
                    item.screenName
                }
            ) { item ->
                HomeScreenCard(
                    item.cardName,
                    onCardClick = { handleCardClick(item) },
                    modifier = Modifier.width(150.dp)
                )
            }
        }
    }

}

@Composable
fun FakeHomeScreenContent() {
    HomeScreenContent(
        canNavBack = false,
        handleCardClick = {},
        cards = NavigationScreen.entries.filter { it != NavigationScreen.Home }
    )
}

@PreviewScreenSizes
@Composable
fun HomeScreenContentPreview() {
    FakeHomeScreenContent()
}