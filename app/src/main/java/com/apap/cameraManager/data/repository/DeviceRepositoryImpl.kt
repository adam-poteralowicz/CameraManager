package com.apap.cameraManager.data.repository

import android.util.Log
import com.apap.cameraManager.data.network.DeviceService
import com.apap.cameraManager.domain.mapper.toDevice
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val service: DeviceService,
) : DeviceRepository {

    override suspend fun getDevices(activeBrandSubdomain: String) = Result.runCatching {
        val devices = service.getDevices(activeBrandSubdomain).map {
            it.map { it.toDevice() }.toTypedArray()
        }.toTypedArray()
        Log.d("WTF", devices.toString())
        devices
    }
}