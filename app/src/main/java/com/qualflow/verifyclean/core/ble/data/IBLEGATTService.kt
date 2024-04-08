package com.qualflow.verifyclean.core.ble.data

import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.DeviceService
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.domain.model.Setting
import kotlinx.coroutines.flow.StateFlow

interface IBLEGATTService {

    val connectionState: StateFlow<ConnectionState>
    val deviceServices: StateFlow<List<DeviceService>>
    fun connectToDevice(scannedDevice: ScannedDevice)
    fun close()
    fun readCharacteristics(uuid: String)
    fun writeBytes(uuid: String, bytes: ByteArray)
    val connectedDevice: StateFlow<ScannedDevice?>
    val setting: StateFlow<Setting?>
}