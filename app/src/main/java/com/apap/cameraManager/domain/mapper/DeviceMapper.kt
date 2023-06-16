package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.domain.model.Device

fun DeviceResponse.toDevice(): Device {
    val deviceName = if (deviceName.isNullOrBlank()) "[NO DEVICE NAME]" else deviceName
    val cameraId = if (cameraId.isNullOrBlank()) "[NO CAMERA ID]" else cameraId
    val ipAddress = if (ipAddress.isNullOrBlank()) "[IP ADDRESS MISSING]" else ipAddress
    return Device(deviceName, cameraId, ipAddress)
}
