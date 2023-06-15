package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.domain.model.Device

fun DeviceResponse.toDevice(): Device {
    val cameraId = cameraId ?: ""
    val deviceStatus = deviceStatus ?: -1
    val ipAddress = ipAddress ?: ""
    return Device(cameraId, deviceStatus, ipAddress)
}
