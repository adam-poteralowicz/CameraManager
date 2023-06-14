package com.apap.cameraManager.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationResponse(
    @Json(name = "token") val token: String?
)