package com.apap.cameraManager.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apap.cameraManager.domain.model.Device
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

    init {
        initialize()
    }

    private fun initialize() = viewModelScope.launch {
        logIn()?.token?.let { token ->
            authorize(token)?.let {
                loadDevices(it.activeBrandSubdomain)
            }
        }
    }

    private fun loadDevices(activeBrandSubdomain: String) = viewModelScope.launch {
        _loadingStateFlow.value = LoadingState.Pending
        with(getDevices(activeBrandSubdomain)) {
            if (this == null) {
                Log.d("MainViewModel", "devices null")
                _loadingStateFlow.value = LoadingState.Failure
            } else {
                Log.d("MainViewModel", "devices: $this")
                _loadingStateFlow.value = LoadingState.Done
                if (isNotEmpty()) {
                    _devicesFlow.value = this
                }
            }
        }
    }
}