package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.apap.cameraManager.R
import com.apap.cameraManager.domain.model.Device

@Composable
fun CameraDetailsScreen(device: Device?, onBackPressedCallback: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            device?.let {
                CameraDetailsCard(it, onBackPressedCallback)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraDetailsCard(device: Device, onBackPressedCallback: () -> Unit) {
    Toolbar(isBackPressAvailable = true, onBackPressedCallback = { onBackPressedCallback() })
    Card(
        modifier = Modifier
            .testTag("camera_details_card")
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            CardTitle(device.deviceName.toString())
            ColoredCardTitleWithText(
                modifier = Modifier.testTag("camera_id_card_text"),
                title = LocalContext.current.getString(R.string.camera_id_title),
                text = device.cameraId.toString(),
                color = Color.Magenta,
            )
            ColoredCardTitleWithText(
                modifier = Modifier.testTag("service_status_card_text"),
                title = LocalContext.current.getString(R.string.service_status_title),
                text = device.serviceStatus.toString(),
                color = Color.Magenta,
            )
            ColoredCardTitleWithText(
                modifier = Modifier.testTag("timezone_card_text"),
                title = LocalContext.current.getString(R.string.timezone_title),
                text = device.timezone.toString(),
                color = Color.Magenta,
            )
            ColoredCardTitleWithText(
                modifier = Modifier.testTag("ip_address_card_text"),
                title = LocalContext.current.getString(R.string.ip_address_title),
                text = device.ipAddress.toString(),
                color = Color.Magenta,
            )
            ColoredCardTitleWithText(
                modifier = Modifier.testTag("owner_account_name_card_text"),
                title = LocalContext.current.getString(R.string.owner_title),
                text = device.ownerAccountName.toString(),
                color = Color.Magenta,
            )
        }
    }
}

@Composable
fun CardTitle(
    text: String,
    fontWeight: FontWeight = FontWeight.W600,
) {
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
        },
        modifier = Modifier.testTag("card_title")
    )
}

@Composable
fun ColoredCardTitleWithText(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    color: Color,
) {
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
                append(" $text")
            }
        },
        modifier = modifier,
    )
}