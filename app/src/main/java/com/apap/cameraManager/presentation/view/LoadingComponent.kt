package com.apap.cameraManager.presentation.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingComponent(
    idle: @Composable () -> Unit = {},
    pending: @Composable () -> Unit = { LoadingIndicator() },
    success: @Composable () -> Unit,
    error: @Composable () -> Unit = {},
    loadingState: LoadingState,
) {
    AnimatedContent(targetState = loadingState) { state ->
        when (state) {
            LoadingState.Done -> success()
            LoadingState.Failure -> error()
            LoadingState.Pending -> pending()
            LoadingState.Idle -> idle()
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .shadow(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

enum class LoadingState {
    Done, Failure, Idle, Pending
}