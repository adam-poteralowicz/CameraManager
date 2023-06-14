package com.apap.cameraManager.domain.usecase

import com.apap.cameraManager.data.repository.LoginRepository
import com.apap.cameraManager.domain.model.Authorization
import javax.inject.Inject

class Authorize @Inject constructor(
    private val repository: LoginRepository,
) {

    suspend operator fun invoke(token: String): Authorization? =
        repository.authorize(token).getOrNull()
}