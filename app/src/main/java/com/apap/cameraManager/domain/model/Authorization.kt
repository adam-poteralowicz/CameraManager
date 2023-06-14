package com.apap.cameraManager.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Authorization(
    val activeBrandSubdomain: String,
    val id: String,
    val userId: String,
): Parcelable