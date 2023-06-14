package com.apap.cameraManager.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Authentication(val token: String) : Parcelable