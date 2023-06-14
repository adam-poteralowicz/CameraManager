package com.apap.cameraManager.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(
    val cameraId: String,
    val deviceStatus: Int,
    val ipAddress: String,
) : Parcelable