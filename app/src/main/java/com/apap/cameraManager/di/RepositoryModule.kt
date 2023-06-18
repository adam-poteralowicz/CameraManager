package com.apap.cameraManager.di

import com.apap.cameraManager.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun loginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun deviceRepository(impl: DeviceRepositoryImpl): DeviceRepository

    companion object {

        @Provides
        @Singleton
        fun provideDevicesCache() = DevicesCache()
    }
}