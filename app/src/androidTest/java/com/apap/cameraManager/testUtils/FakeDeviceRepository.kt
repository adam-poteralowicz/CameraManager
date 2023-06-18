package com.apap.cameraManager.testUtils

import com.apap.cameraManager.data.repository.DeviceRepository
import com.apap.cameraManager.domain.model.Device

internal class FakeDeviceRepository : DeviceRepository {

    private var fakeDevices: Array<Array<Device>> = arrayOf(arrayOf())

    override suspend fun getDevices(activeBrandSubdomain: String) = if (fakeDevices.isEmpty()) {
        Result.failure(Throwable("Failure"))
    } else {
        Result.success(fakeDevices)
    }

    fun setDevices(devices: Array<Array<Device>>) {
        fakeDevices = devices
    }
}