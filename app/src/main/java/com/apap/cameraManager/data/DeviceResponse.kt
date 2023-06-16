package com.apap.cameraManager.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceResponse(
    @Json(name = "name") val deviceName: String?,
    @Json(name = "id") val cameraId: String?,
    @Json(name = "ip_address") val ipAddress: String?
)