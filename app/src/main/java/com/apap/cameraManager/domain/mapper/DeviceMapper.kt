package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.domain.model.Device

fun DeviceResponse.toDevice() = Device(cameraId, deviceStatus, ipAddress)