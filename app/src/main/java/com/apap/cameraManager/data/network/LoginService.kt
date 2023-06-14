package com.apap.cameraManager.data.network

import com.apap.cameraManager.data.AuthenticationResponse
import com.apap.cameraManager.data.AuthorizationResponse
import retrofit2.http.POST
import retrofit2.http.Query

private const val LOGIN_BASE_URL = "https://login.eagleeyenetworks.com/g/aaa"

interface LoginService {

    @POST("$LOGIN_BASE_URL/authenticate")
    suspend fun authenticate(
        @Query("username") username: String,
        @Query("password") password: String,
    ): AuthenticationResponse

    @POST("$LOGIN_BASE_URL/authorize")
    suspend fun authorize(
        @Query("token") token: String,
    ): AuthorizationResponse
}