package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apap.cameraManager.domain.model.Device

@Composable
fun CameraDetailsScreen(device: Device?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
        ) {
            device?.let {
                CameraDetailsCard(it)
            }
        }
    }
}

@Composable
fun CameraDetailsCard(device: Device) {
    CameraDetailsSpacer()
    CameraDetailsText(device.deviceName)
    CameraDetailsSpacer()
    CameraDetailsText(device.cameraId)
    CameraDetailsSpacer()
    CameraDetailsText(device.ipAddress)
    CameraDetailsSpacer()
}

@Composable
fun CameraDetailsSpacer() {
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun CameraDetailsText(text: String?) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = text.toString(),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.Monospace
    )
}