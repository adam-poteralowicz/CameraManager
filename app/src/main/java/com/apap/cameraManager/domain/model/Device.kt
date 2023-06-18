package com.apap.cameraManager.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(
    val deviceName: String?,
    val cameraId: String?,
    val serviceStatus: String?,
    val ipAddress: String?,
    val ownerAccountName: String?,
    val timezone: String?,
) : Parcelable {
    override fun toString() =
        "Device Name: $deviceName, CameraID: $cameraId, IP Address: $ipAddress"
}

