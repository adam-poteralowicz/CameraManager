package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apap.cameraManager.domain.model.Device
import com.apap.cameraManager.presentation.viewModel.MainViewModel
import com.apap.cameraManager.R

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToCameraDetails: (Device) -> Unit,
) {

    val devices by viewModel.devicesFlow.collectAsState()
    val state by viewModel.loadingStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
    ) {
        Toolbar()
        Column(Modifier.align(Alignment.CenterHorizontally)) {
            LoadingComponent(
                idle = {
                    CameraManagerLoadingImage()
                },
                success = {
                    Column(modifier = Modifier.padding(8.dp)) {
                        devices?.let {
                            DevicesList(it, navigateToCameraDetails)
                        }
                    }
                },
                error = {
                    CameraManagerError()
                },
                loadingState = state,
            )
        }
    }
}

@Composable
fun DevicesList(devices: List<Device>, onItemClicked: (Device) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(devices) {
                DeviceItem(it, onItemClicked)
            }
        }
    }
}

@Composable
fun DeviceItem(
    device: Device,
    onItemClicked: (Device) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.DarkGray)
            .clickable {
                onItemClicked(device)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = device.deviceName.toString(), color = Color.White)
    }
}

@Composable
fun CameraManagerError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(id = R.drawable.camera_icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Text(text = "No camera devices detected", color = Color.Red, fontSize = 24.sp)
        }
    }
}

@Composable
fun CameraManagerLoadingImage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(id = R.drawable.camera_icon),
                contentDescription = null,
            )
        }
    }
}