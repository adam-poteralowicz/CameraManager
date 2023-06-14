package com.apap.cameraManager.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorizationResponse(
    @Json(name = "active_brand_subdomain") val activeBrandSubdomain: String?,
    @Json(name = "id") val id: String?,
    @Json(name = "user_id") val userId: String?,
)