package com.apap.cameraManager.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apap.cameraManager.domain.model.Device
import com.apap.cameraManager.domain.model.FailureCause
import com.apap.cameraManager.domain.usecase.Authorize
import com.apap.cameraManager.domain.usecase.GetDevices
import com.apap.cameraManager.domain.usecase.LogIn
import com.apap.cameraManager.presentation.view.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authorize: Authorize,
    private val getDevices: GetDevices,
    private val logIn: LogIn,
) : ViewModel() {

    private val _devicesFlow = MutableStateFlow<List<Device>?>(null)
    val devicesFlow = _devicesFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow(LoadingState.Idle)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    private val _failureCauseFlow = MutableStateFlow<FailureCause>(FailureCause.None)
    val failureCauseFlow = _failureCauseFlow.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() = viewModelScope.launch {
        _loadingStateFlow.value = LoadingState.Pending
        logIn()?.token?.let { token ->
            authorize(token)?.let {
                loadDevices(it.activeBrandSubdomain)
            } ?: run { emitFailure(failureCause = FailureCause.Authentication) }
        } ?: run { emitFailure(failureCause = FailureCause.Authentication) }
    }

    private fun loadDevices(activeBrandSubdomain: String) = viewModelScope.launch {
        val devices = getDevices(activeBrandSubdomain)
        with(devices) {
            if (this == null || isEmpty()) {
                emitFailure(failureCause = FailureCause.Data)
            } else {
                emitSuccess()
            }
            if (this != null && isNotEmpty()) {
                _devicesFlow.value = this
            }
        }
    }

    private fun emitFailure(failureCause: FailureCause) {
        _loadingStateFlow.value = LoadingState.Failure
        _failureCauseFlow.value = failureCause
    }

    private fun emitSuccess() {
        _loadingStateFlow.value = LoadingState.Done
    }
}