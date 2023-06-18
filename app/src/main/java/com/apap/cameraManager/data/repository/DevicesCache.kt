package com.apap.cameraManager.data.repository

import com.apap.cameraManager.domain.model.Device
import javax.inject.Singleton

@Singleton
open class DevicesCache {
    private lateinit var value: List<Device>

    open fun retrieve() : List<Device> = if (this::value.isInitialized) value else emptyList()
    open fun save(devices: List<Device>) {
        value = devices
    }
}