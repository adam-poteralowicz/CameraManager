package com.apap.cameraManager.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceResponse(
    @Json(name = "name") val deviceName: String?,
    @Json(name = "id") val cameraId: String?,
    @Json(name = "service_status") val serviceStatus: String?,
    @Json(name = "ip_address") val ipAddress: String?,
    @Json(name = "owner_account_name") val ownerAccountName: String?,
    @Json(name = "timezone") val timezone: String?,
)