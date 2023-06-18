package com.apap.cameraManager.testUtils

import com.apap.cameraManager.data.repository.LoginRepository
import com.apap.cameraManager.domain.model.Authentication
import com.apap.cameraManager.domain.model.Authorization

class FakeLoginRepository : LoginRepository {

    private var fakeToken: String? = null
    private var authorized: Boolean = false

    override suspend fun authorize(token: String): Result<Authorization> = if (authorized) {
        Result.success(Authorization(fakeToken!!, "id", "id"))
    } else {
        Result.failure(Throwable("Failure"))
    }

    override suspend fun logIn(username: String, password: String): Result<Authentication> =
        if (fakeToken.isNullOrBlank()) {
            Result.failure(Throwable("Failure"))
        } else {
            Result.success(Authentication(fakeToken!!))
        }

    fun setToken(token: String) {
        fakeToken = token
    }

    fun setAuthorized(isAuthorized: Boolean) {
        authorized = isAuthorized
    }
}