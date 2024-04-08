package com.qualflow.verifyclean.feature.precleaning.presentation

import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.domain.model.CleanStatus
import com.qualflow.verifyclean.core.domain.model.Setting

data class PreCleaningUiState(
    val connectedDevice: ScannedDevice? = null,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val settingLoading: Setting? = null,
    val isCleaningInitialising: Boolean = false,
    val isSettingLoaded: Boolean = false
)
