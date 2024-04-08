package com.qualflow.verifyclean.presentation.components

import androidx.compose.runtime.Composable
import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.ble.domain.model.isConnected

@Composable
fun CleanTopSlotInformation(
    connectionState: ConnectionState,
    connectedDevice: ScannedDevice?,
    shouldShowHardwareType: Boolean = true
) {
    if (connectionState.isConnected())
        HardwareInformation(
            serialNumber = connectedDevice?.deviceName ?: "unknown",
            shouldShowHardwareType = shouldShowHardwareType
        )
    else
        DisconnectedIndicator()
}