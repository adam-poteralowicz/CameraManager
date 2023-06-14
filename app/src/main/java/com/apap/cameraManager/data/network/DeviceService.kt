package com.apap.cameraManager.data.network

import com.apap.cameraManager.data.DeviceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DeviceService {

    @GET("https://{activeBrandSubdomain}.eagleeyenetworks.com/g/device/list")
    suspend fun getDevices(
        @Path("activeBrandSubdomain") activeBrandSubdomain: String
    ): List<DeviceResponse>
}