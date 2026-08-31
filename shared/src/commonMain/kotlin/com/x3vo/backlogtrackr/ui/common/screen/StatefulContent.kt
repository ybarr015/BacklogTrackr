package com.x3vo.backlogtrackr.ui.common.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun StatefulContent(
    isLoading: Boolean,
    error: String?,
    content: @Composable () -> Unit

) {

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (error != null) {
        Text(
            text = error,
            color = Color.Red
        )
    } else {
        content()
    }

}