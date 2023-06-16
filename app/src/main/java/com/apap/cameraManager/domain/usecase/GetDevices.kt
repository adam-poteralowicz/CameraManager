package com.apap.cameraManager.domain.usecase

import com.apap.cameraManager.data.repository.DeviceRepository
import com.apap.cameraManager.domain.model.Device
import javax.inject.Inject

class GetDevices @Inject constructor(
    private val repository: DeviceRepository,
) {

    suspend operator fun invoke(activeBrandSubdomain: String): List<Device>? =
        if (activeBrandSubdomain.isBlank()) {
            null
        } else repository.getDevices(activeBrandSubdomain).getOrNull()?.flatten()
}