package com.qualflow.verifyclean.feature.connection.presentation

import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice

data class ConnectionUiState(
    val isScanning: Boolean = false,
    val scannedDevices: List<ScannedDevice> = listOf(),
    val deviceDetail: DeviceDetail? = null,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val connectionError: String? = null
)
