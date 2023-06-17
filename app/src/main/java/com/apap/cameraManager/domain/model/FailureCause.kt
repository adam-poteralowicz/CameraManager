package com.apap.cameraManager.domain.model

import com.apap.cameraManager.R

sealed class FailureCause(val messageResId: Int = -1) {
    object Authentication : FailureCause(messageResId = R.string.auth_failure_msg)
    object Data : FailureCause(messageResId = R.string.data_failure_msg)
    object None : FailureCause()
}