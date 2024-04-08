package com.qualflow.verifyclean.core.ble.data.repository

import com.qualflow.verifyclean.core.ble.domain.model.BleScanEvent
import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.core.ble.domain.model.DeviceService
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.domain.model.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IBLERepository {

    val bleScanEvent: StateFlow<BleScanEvent>
    val connectionState: StateFlow<ConnectionState>
    val deviceServices: StateFlow<List<DeviceService>>
    val connectedDevice: StateFlow<ScannedDevice?>
    val setting: StateFlow<Setting?>
    suspend fun scanLeDevice()
    fun stopLeScan()
    fun connect(scannedDevice: ScannedDevice)
    fun close()
    fun writeBytes(uuid: String, values: ByteArray)
}