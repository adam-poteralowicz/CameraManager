package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.apap.cameraManager.R

@Composable
fun Toolbar() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                fontWeight = FontWeight.Normal
            )
        },
    )
}