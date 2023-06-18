package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.DeviceResponse
import com.apap.cameraManager.data.network.DeviceService
import com.apap.cameraManager.domain.model.Device
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class DeviceMapperTest {

    @Test
    fun `correctly maps device response to domain model`() {
        val response = deviceResponse()
        val expectedModel = device()

        val device = response.toDevice()

        assertThat(device).isEqualTo(expectedModel)
    }

    @Test
    fun `provides placeholder value when device name is null`() {
        val response = deviceResponse(deviceName = null)
        val expectedModel = device(deviceName = "[NO DEVICE NAME]")

        val device = response.toDevice()

        assertThat(device.deviceName).isEqualTo(expectedModel.deviceName)
    }

    @Test
    fun `provides placeholder value when camera id is null`() {
        val response = deviceResponse(cameraId = null)
        val expectedModel = device(cameraId = "[NO CAMERA ID]")

        val device = response.toDevice()

        assertThat(device.cameraId).isEqualTo(expectedModel.cameraId)
    }

    @Test
    fun `provides placeholder value when ip address is null`() {
        val response = deviceResponse(ipAddress = null)
        val expectedModel = device(ipAddress = "[IP ADDRESS MISSING]")

        val device = response.toDevice()

        assertThat(device.ipAddress).isEqualTo(expectedModel.ipAddress)
    }

    @Test
    fun `provides placeholder value when owner is blank`() {
        val response = deviceResponse(owner = "")
        val expectedModel = device(owner = "[NO OWNER FOUND]")

        val device = response.toDevice()

        assertThat(device.ownerAccountName).isEqualTo(expectedModel.ownerAccountName)
    }

    @Test
    fun `provides placeholder value when timezone is blank`() {
        val response = deviceResponse(timezone = "")
        val expectedModel = device(timezone = "[NO TIMEZONE]")

        val device = response.toDevice()

        assertThat(device.timezone).isEqualTo(expectedModel.timezone)
    }

    private fun deviceResponse(
        deviceName: String? = "Amsterdam Cam",
        cameraId: String? = "1",
        ipAddress: String? = "127.0.0.1",
        owner: String? = "John Doe",
        timezone: String? = "US/Central",
        serviceStatus: String? = "ATTD",
    ) = DeviceResponse(deviceName, cameraId, ipAddress, owner, timezone, serviceStatus)

    private fun device(
        deviceName: String? = "Amsterdam Cam",
        cameraId: String? = "1",
        ipAddress: String? = "127.0.0.1",
        owner: String? = "John Doe",
        timezone: String? = "US/Central",
        serviceStatus: String? = "ATTD",
    ) = Device(deviceName, cameraId, ipAddress, owner, timezone, serviceStatus)
}