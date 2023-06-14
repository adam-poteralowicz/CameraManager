package com.apap.cameraManager.data.repository

import com.apap.cameraManager.data.network.LoginService
import com.apap.cameraManager.domain.mapper.toAuthentication
import com.apap.cameraManager.domain.mapper.toAuthorization
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: LoginService,
) : LoginRepository {

    override suspend fun logIn(username: String, password: String) = Result.runCatching {
        service.authenticate(username, password).toAuthentication()
    }

    override suspend fun authorize(token: String) = Result.runCatching {
        service.authorize(token).toAuthorization()
    }
}