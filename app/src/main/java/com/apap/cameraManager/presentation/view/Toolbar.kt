package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.apap.cameraManager.R

@Composable
fun Toolbar(isBackPressAvailable: Boolean = false, onBackPressedCallback: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                fontWeight = FontWeight.Normal
            )
        },
        navigationIcon = {
            if (isBackPressAvailable) {
                Image(
                    painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onBackPressedCallback() }
                )
            }
        },
    )
}