package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.apap.cameraManager.R
import com.apap.cameraManager.domain.model.Device

@Composable
fun CameraDetailsScreen(device: Device?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            device?.let {
                CameraDetailsCard(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraDetailsCard(device: Device) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            CardText(device.deviceName.toString())
            ColoredCardTitleWithText(
                title = LocalContext.current.getString(R.string.camera_id_title),
                text = device.cameraId.toString(),
                color = Color.Magenta,
            )
            ColoredCardTitleWithText(
                title = LocalContext.current.getString(R.string.ip_address_title),
                text = device.ipAddress.toString(),
                color = Color.Magenta,
            )
        }
    }
}

@Composable
fun CardText(text: String, fontWeight: FontWeight = FontWeight.W600) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = fontWeight,
                    fontFamily = FontFamily.SansSerif,
                )
            ) {
                append(text)
            }
        }
    )
}

@Composable
fun ColoredCardTitleWithText(title: String, text: String, color: Color) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily.SansSerif,
                    color = color,
                )
            ) {
                append(title)
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                )
            ) {
                append(text)
            }
        }
    )
}