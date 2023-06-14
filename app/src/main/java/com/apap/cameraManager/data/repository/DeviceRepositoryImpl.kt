package com.apap.cameraManager.data.repository

import com.apap.cameraManager.data.network.DeviceService
import com.apap.cameraManager.domain.mapper.toDevice
import com.apap.cameraManager.domain.model.Device
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val service: DeviceService,
) : DeviceRepository {

    override suspend fun getDevices(activeBrandSubdomain: String): Result<List<Device>> =
        Result.runCatching {
            service.getDevices(activeBrandSubdomain).map { it.toDevice() }
        }
}