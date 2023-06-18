package com.apap.cameraManager.presentation.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apap.cameraManager.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingComponent(
    idle: @Composable () -> Unit = {},
    pending: @Composable () -> Unit = { LoadingImage() },
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
    Box(modifier = Modifier.wrapContentSize()) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier.testTag("loader").size(60.dp)
            )
        }
    }
}

@Composable
fun LoadingImage() {
    Box(
        modifier = Modifier
            .testTag("loading_image")
            .wrapContentWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoadingIndicator()
        }
    }
}

enum class LoadingState {
    Done, Failure, Idle, Pending
}