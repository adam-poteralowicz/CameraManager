package com.apap.cameraManager.presentation.view

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apap.cameraManager.domain.model.Device
import com.apap.cameraManager.presentation.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToCameraDetails: (Device) -> Unit,
) {

    val devices by viewModel.devicesFlow.collectAsState()
    val state by viewModel.loadingStateFlow.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
    ) {
        Toolbar()
        LoadingComponent(
            success = {
                devices?.let {
                    Column(Modifier.fillMaxWidth()) {
                        DevicesList(it, navigateToCameraDetails)
                    }
                }
            },
            loadingState = state,
        )

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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clickable {
                onItemClicked(device)
            }
    ) {
        Text(text = device.cameraId)
        Text(text = device.ipAddress)
        Text(text = device.deviceStatus.toString())
    }
}