package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.domain.model.Device

fun DeviceResponse.toDevice() = Device(
    cameraId = cameraId ?: throw ApiParseException("cameraId == null"),
    deviceStatus = deviceStatus ?: throw ApiParseException("deviceStatus == null"),
    ipAddress = ipAddress ?: throw ApiParseException("ipAddress == null")
)