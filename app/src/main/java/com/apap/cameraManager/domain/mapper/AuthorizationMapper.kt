package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.AuthorizationResponse
import com.apap.cameraManager.domain.model.Authorization

fun AuthorizationResponse.toAuthorization() = Authorization(
    activeBrandSubdomain = activeBrandSubdomain ?: throw ApiParseException("activeBrandSubdomain == null"),
    id = id ?: throw ApiParseException("id == null"),
    userId = userId ?: throw ApiParseException("userId == null")
)