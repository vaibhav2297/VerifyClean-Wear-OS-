package com.qualflow.verifyclean.feature.cleaning.presentation

import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.domain.model.CleanStatus

data class CleaningUiState(
    //connection state
    val connectedDevice: ScannedDevice? = null,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val connectionError: String? = null,

    //cleaning state
    val cleanStatus: CleanStatus = CleanStatus.Idle,
    val currentStep: Int = 0,
    val totalSteps: Int = 0,
    val isCleanFinish: Boolean = false
)
