package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apap.cameraManager.domain.model.Device
import com.apap.cameraManager.presentation.viewModel.MainViewModel
import com.apap.cameraManager.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToCameraDetails: (Device) -> Unit,
) {

    val devices by viewModel.devicesFlow.collectAsState()
    val state by viewModel.loadingStateFlow.collectAsState()
    val failureCause by viewModel.failureCauseFlow.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    var searchInput by rememberSaveable { mutableStateOf("") }
    val themeColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(themeColor)
            .testTag("main_screen")
    ) {
        Toolbar(modifier = Modifier.testTag("toolbar"))
        Column(Modifier.align(Alignment.CenterHorizontally)) {
            LoadingComponent(
                success = {
                    Column(modifier = Modifier.padding(8.dp)) {
                        devices?.let { devices ->
                            SearchBar(
                                modifier = Modifier.testTag("search_bar"),
                                value = searchInput,
                                onValueChange = { value ->
                                    searchInput = value
                                    viewModel.onDeviceSearch(value)
                                },
                                onDone = {
                                    keyboardController?.hide()
                                    viewModel.onDeviceSearch(searchInput)
                                }
                            )
                            DevicesList(devices, navigateToCameraDetails)
                        }
                    }
                },
                error = { CameraManagerError(messageResId = failureCause.messageResId) },
                loadingState = state,
            )
        }
    }
}

@Composable
fun DevicesList(devices: List<Device>, onItemClicked: (Device) -> Unit) {
    Row(
        modifier = Modifier.testTag("devices_list").fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        if (devices.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = LocalContext.current.getString(R.string.search_failure_msg),
                color = Color.Red,
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(devices) {
                    DeviceItem(it, onItemClicked)
                }
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
fun CameraManagerError(messageResId: Int) {
    Box(
        modifier = Modifier.testTag("camera_manager_error").fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Text(
                text = LocalContext.current.getString(messageResId),
                color = Color.Red,
                fontSize = 24.sp
            )
        }
    }
}