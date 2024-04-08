package com.qualflow.verifyclean.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualflow.verifyclean.core.ble.data.IBLEGATTService
import com.qualflow.verifyclean.core.ble.data.IBLEScanService
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.feature.connection.presentation.ConnectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val bleRepository: IBLERepository,
) : ViewModel() {

    private val _connectedDevice = bleRepository.connectedDevice
    private val _connectionState = bleRepository.connectionState

    val uiState = combine(
        _connectedDevice,
        _connectionState
    ) { device, connectionState ->
        DashboardUiState(
            connectionState = connectionState,
            connectedDevice = device
        )
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            DashboardUiState()
        )


}