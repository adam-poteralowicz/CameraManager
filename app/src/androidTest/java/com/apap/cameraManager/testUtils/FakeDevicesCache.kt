package com.apap.cameraManager.testUtils

import com.apap.cameraManager.data.repository.DevicesCache
import com.apap.cameraManager.domain.model.Device

internal class FakeDevicesCache : DevicesCache() {

    private lateinit var value: List<Device>

    override fun retrieve() : List<Device> = if (this::value.isInitialized) value else emptyList()
    override fun save(devices: List<Device>) {
        value = devices
    }
}