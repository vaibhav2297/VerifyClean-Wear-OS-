package com.qualflow.verifyclean.feature.connection.presentation

import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice

sealed interface ConnectionUiEvent {

    data class OnConnectionEstablish(
        val device: ScannedDevice
    ) : ConnectionUiEvent

    data object OnStartScan : ConnectionUiEvent

    data object OnStopScan : ConnectionUiEvent

    data object OnGetDeviceInfo : ConnectionUiEvent
}