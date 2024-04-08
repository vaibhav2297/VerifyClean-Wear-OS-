package com.qualflow.verifyclean.core.ble.domain.model

data class DeviceDetail(
    val device: ScannedDevice?,
    val services: List<DeviceService>,
    val connectionState: ConnectionState
)

data class DeviceService(
    val uuid: String,
    val name: String,
    val characteristics: List<DeviceCharacteristics>
)
