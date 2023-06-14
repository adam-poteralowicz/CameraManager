package com.apap.cameraManager.data.repository

import com.apap.cameraManager.domain.model.Authentication
import com.apap.cameraManager.domain.model.Authorization

interface LoginRepository {

    suspend fun authorize(token: String): Result<Authorization>
    suspend fun logIn(username: String, password: String): Result<Authentication>
}