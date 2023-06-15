package com.apap.cameraManager.di

import com.apap.cameraManager.data.DeviceResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReader.Token.*
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class DeviceResponseJsonAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): DeviceResponse {
        var deviceStatus: Int? = null
        var cameraId: String? = null
        var ipAddress: String? = null
        var counter = 0

        with(reader) {
            while (hasNext()) {
                when (counter) {
                    0 -> {
                        if (reader.peek() == STRING) {
                            cameraId = nextString()
                            if (cameraId == null) {
                                cameraId = ""
                            }
                        }
                    }
                    9 -> {
                        if (reader.peek() == NUMBER) {
                            deviceStatus = nextInt()
                            if (deviceStatus == null) {
                                deviceStatus = -1
                            }
                        }
                    }
                    13 -> {
                        if (reader.peek() == STRING) {
                            ipAddress = nextString()
                            if (ipAddress == null) {
                                ipAddress = ""
                            }
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
        }
        return DeviceResponse(deviceStatus, cameraId, ipAddress)
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: DeviceResponse?) {
    }
}