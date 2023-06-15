package com.apap.cameraManager.di

import com.apap.cameraManager.data.DeviceResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReader.Token.*
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class DeviceResponseJsonAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): Array<DeviceResponse>? {
        var deviceStatus: Int? = null
        var cameraId: String? = null
        var ipAddress: String? = null
        var counter = -2

        with(reader) {
            beginArray()
            while (hasNext()) {
                when (counter) {
                    0 -> {
                        if (reader.peek() == STRING) {
                            cameraId = nextString()
                            if (cameraId == null) {
                                return null
                            }
                        }
                    }
                    9 -> {
                        if (reader.peek() == NUMBER) {
                            deviceStatus = nextInt()
                            if (deviceStatus == null) {
                                return null
                            }
                        }
                    }
                    13 -> {
                        if (reader.peek() == STRING) {
                            ipAddress = nextString()
                            if (ipAddress == null) {
                                return null
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
            endArray()
        }
        return arrayOf(DeviceResponse(deviceStatus, cameraId, ipAddress))
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: DeviceResponse?) {
    }
}