package com.apap.cameraManager.domain.usecase

import com.apap.cameraManager.data.repository.LoginRepository
import com.apap.cameraManager.domain.model.Authentication
import javax.inject.Inject

private const val USERNAME = "demomobile@eagleeyenetworks.com"
private const val PASSWORD = "workingwell"

class LogIn @Inject constructor(
    private val repository: LoginRepository,
) {

    suspend operator fun invoke(): Authentication? = repository.logIn(
        username = USERNAME,
        password = PASSWORD,
    ).getOrNull()
}