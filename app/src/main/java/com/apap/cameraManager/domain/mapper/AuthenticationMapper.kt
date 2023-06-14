package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.AuthenticationResponse
import com.apap.cameraManager.domain.model.Authentication

fun AuthenticationResponse.toAuthentication() = Authentication(
    token = token ?: throw ApiParseException("token == null")
)