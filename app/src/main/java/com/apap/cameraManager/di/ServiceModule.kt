package com.apap.cameraManager.di

import com.apap.cameraManager.data.network.DeviceService
import com.apap.cameraManager.data.network.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun loginService(retrofit: Retrofit): LoginService = retrofit.create()

    @Provides
    fun deviceService(retrofit: Retrofit): DeviceService = retrofit.create()
}