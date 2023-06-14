package com.apap.cameraManager.di

import com.apap.cameraManager.data.repository.DeviceRepository
import com.apap.cameraManager.data.repository.DeviceRepositoryImpl
import com.apap.cameraManager.data.repository.LoginRepository
import com.apap.cameraManager.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun loginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun deviceRepository(impl: DeviceRepositoryImpl): DeviceRepository
}