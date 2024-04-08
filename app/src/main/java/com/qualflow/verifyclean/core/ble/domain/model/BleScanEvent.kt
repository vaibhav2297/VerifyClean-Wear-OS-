package com.qualflow.verifyclean.core.ble.domain.model

data class BleScanEvent(
    val isScanning: Boolean = false,
    val scannedDevice: List<ScannedDevice> = listOf(),
    val error: String? = null
)