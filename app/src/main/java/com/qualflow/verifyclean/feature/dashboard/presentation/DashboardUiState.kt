package com.qualflow.verifyclean.feature.dashboard.presentation

import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice

data class DashboardUiState(
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val connectedDevice: ScannedDevice? = null
)
