package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.domain.model.Device

private const val DEVICE_NAME_PLACEHOLDER = "[NO DEVICE NAME]"
private const val CAMERA_ID_PLACEHOLDER = "[NO CAMERA ID]"
private const val IP_ADDRESS_PLACEHOLDER = "[IP ADDRESS MISSING]"

fun DeviceResponse.toDevice(): Device {
    val deviceName = if (deviceName.isNullOrBlank()) DEVICE_NAME_PLACEHOLDER else deviceName
    val cameraId = if (cameraId.isNullOrBlank()) CAMERA_ID_PLACEHOLDER else cameraId
    val ipAddress = if (ipAddress.isNullOrBlank()) IP_ADDRESS_PLACEHOLDER else ipAddress
    return Device(deviceName, cameraId, ipAddress)
}
