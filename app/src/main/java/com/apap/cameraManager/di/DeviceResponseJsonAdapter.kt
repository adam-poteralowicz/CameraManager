package com.apap.cameraManager.di

import com.apap.cameraManager.data.DeviceResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReader.Token.*
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class DeviceResponseJsonAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): Array<DeviceResponse> {
        var deviceName: String? = null
        var cameraId: String? = null
        var serviceStatus: String? = null
        var ipAddress: String? = null
        var ownerAccountName: String? = null
        var timezone: String? = null
        var counter = -1 // counter set to -1 to consume initial BEGIN_ARRAY

        with(reader) {
            beginArray()
            while (hasNext()) {
                when (counter) {
                    0 -> {
                        if (reader.peek() == STRING) {
                            cameraId = nextString()
                        }
                    }
                    1 -> {
                        if (reader.peek() == STRING) {
                            deviceName = nextString()
                        }
                    }
                    4 -> {
                        if (reader.peek() == STRING) {
                            serviceStatus = nextString()
                        }
                    }
                    10 -> {
                        if (reader.peek() == STRING) {
                            timezone = nextString()
                        }
                    }
                    13 -> {
                        if (reader.peek() == STRING) {
                            ipAddress = nextString()
                        }
                    }
                    15 -> {
                        if (reader.peek() == STRING) {
                            ownerAccountName = nextString()
                        }
                    }
                    else -> {
                        when (reader.peek()) {
                            NAME -> skipName()
                            else -> skipValue()
                        }
                    }
                }
                counter++
            }
            endArray()
        }
        require(deviceName != null)
        require(cameraId != null)
        require(ipAddress != null)
        require(ownerAccountName != null)
        require(timezone != null)
        require(serviceStatus != null)
        return arrayOf(
            DeviceResponse(deviceName, cameraId, serviceStatus, ipAddress, ownerAccountName, timezone)
        )
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: DeviceResponse?) {
    }
}