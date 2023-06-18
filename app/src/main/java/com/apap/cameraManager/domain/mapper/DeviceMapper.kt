package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.domain.model.Device

private const val DEVICE_NAME_PLACEHOLDER = "[NO DEVICE NAME]"
private const val CAMERA_ID_PLACEHOLDER = "[NO CAMERA ID]"
private const val IP_ADDRESS_PLACEHOLDER = "[IP ADDRESS MISSING]"
private const val OWNER_PLACEHOLDER = "[NO OWNER FOUND]"
private const val TIMEZONE_PLACEHOLDER = "[NO TIMEZONE]"

fun DeviceResponse.toDevice(): Device {
    val deviceName = if (deviceName.isNullOrBlank()) DEVICE_NAME_PLACEHOLDER else deviceName
    val cameraId = if (cameraId.isNullOrBlank()) CAMERA_ID_PLACEHOLDER else cameraId
    val ipAddress = if (ipAddress.isNullOrBlank()) IP_ADDRESS_PLACEHOLDER else ipAddress
    val ownerAccountName = if (ownerAccountName.isNullOrBlank()) OWNER_PLACEHOLDER else ownerAccountName
    val timezone = if (timezone.isNullOrBlank()) TIMEZONE_PLACEHOLDER else timezone
    return Device(deviceName, cameraId, serviceStatus, ipAddress, ownerAccountName, timezone)
}
