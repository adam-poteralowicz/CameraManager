package com.apap.cameraManager.data.repository

import com.apap.cameraManager.domain.model.Device
import javax.inject.Singleton

@Singleton
class DevicesCache {
    private lateinit var value: List<Device>

    fun retrieve() : List<Device> = if (this::value.isInitialized) value else emptyList()
    fun save(devices: List<Device>) {
        value = devices
    }
}