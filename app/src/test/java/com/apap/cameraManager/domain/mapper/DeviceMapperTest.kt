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
        val owner = "owner"
        val timezone = "US/Central"
        val serviceStatus = "ATTD"
        val response = DeviceResponse(deviceName, cameraId, serviceStatus, ipAddress, owner, timezone)
        val expectedModel = Device(deviceName, cameraId, serviceStatus, ipAddress, owner, timezone)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when device name is null`() {
        val deviceName = null
        val cameraId = "1"
        val ipAddress = "127.0.0.1"
        val owner = "owner"
        val timezone = "US/Central"
        val serviceStatus = "ATTD"
        val response = DeviceResponse(deviceName, cameraId, serviceStatus, ipAddress, owner, timezone)
        val expectedModel = Device("[NO DEVICE NAME]", cameraId, serviceStatus, ipAddress, owner, timezone)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when camera id is null`() {
        val deviceName = "Amsterdam Cam"
        val cameraId = null
        val ipAddress = "127.0.0.1"
        val owner = "owner"
        val timezone = "US/Central"
        val serviceStatus = "ATTD"
        val response = DeviceResponse(deviceName, cameraId, serviceStatus, ipAddress, owner, timezone)
        val expectedModel = Device(deviceName, "[NO CAMERA ID]", serviceStatus, ipAddress, owner, timezone)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when ip address is null`() {
        val deviceName = "Amsterdam Cam"
        val cameraId = "1"
        val ipAddress = null
        val owner = "owner"
        val timezone = "US/Central"
        val serviceStatus = "ATTD"
        val response = DeviceResponse(deviceName, cameraId, serviceStatus, ipAddress, owner, timezone)
        val expectedModel = Device(deviceName, cameraId, serviceStatus, "[IP ADDRESS MISSING]", owner, timezone)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when owner is blank`() {
        val deviceName = "Amsterdam Cam"
        val cameraId = "1"
        val ipAddress = "123.456.789"
        val owner = ""
        val timezone = "US/Central"
        val serviceStatus = "ATTD"
        val response = DeviceResponse(deviceName, cameraId, serviceStatus, ipAddress, owner, timezone)
        val expectedModel = Device(deviceName, cameraId, serviceStatus, ipAddress, "[NO OWNER FOUND]", timezone)

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when timezone is blank`() {
        val deviceName = "Amsterdam Cam"
        val cameraId = "1"
        val ipAddress = "123.456.789"
        val owner = "John Doe"
        val timezone = ""
        val serviceStatus = "ATTD"
        val response = DeviceResponse(deviceName, cameraId, serviceStatus, ipAddress, owner, timezone)
        val expectedModel = Device(deviceName, cameraId, serviceStatus, ipAddress, owner, "[NO TIMEZONE]")

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }
}