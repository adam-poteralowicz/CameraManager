package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.domain.model.Device
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DeviceMapperTest {

    @Test
    fun `correctly maps device response to domain model`() {
        val deviceName = "Amsterdam Cam"
        val cameraId = "1"
        val ipAddress = "127.0.0.1"
        val response = DeviceResponse(deviceName, cameraId, ipAddress)
        val expectedModel = Device(deviceName, cameraId, ipAddress)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when device name is null`() {
        val deviceName = null
        val cameraId = "1"
        val ipAddress = "127.0.0.1"
        val response = DeviceResponse(deviceName, cameraId, ipAddress)
        val expectedModel = Device("[NO DEVICE NAME]", cameraId, ipAddress)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when camera id is null`() {
        val deviceName = "Amsterdam Cam"
        val cameraId = null
        val ipAddress = "127.0.0.1"
        val response = DeviceResponse(deviceName, cameraId, ipAddress)
        val expectedModel = Device(deviceName, "[NO CAMERA ID]", ipAddress)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when ip address is null`() {
        val deviceName = "Amsterdam Cam"
        val cameraId = "1"
        val ipAddress = null
        val response = DeviceResponse(deviceName, cameraId, ipAddress)
        val expectedModel = Device(deviceName, cameraId, "[IP ADDRESS MISSING]")

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }
}