package com.apap.cameraManager.data.repository

import com.apap.cameraManager.domain.model.Device

interface DeviceRepository {

    suspend fun getDevices(activeBrandSubdomain: String): Result<Array<Array<Device>>>
}