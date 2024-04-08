package com.qualflow.verifyclean.core.ble.domain.repository

import com.qualflow.verifyclean.core.ble.data.IBLEGATTService
import com.qualflow.verifyclean.core.ble.data.IBLEScanService
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.ble.domain.model.BleScanEvent
import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.core.ble.domain.model.DeviceService
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.domain.model.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class BLERepository @Inject constructor(
    private val bleGattService: IBLEGATTService,
    private val bleScanService: IBLEScanService
) : IBLERepository {

    override val bleScanEvent : StateFlow<BleScanEvent>  = bleScanService.scanEvent

    override val connectionState : StateFlow<ConnectionState> = bleGattService.connectionState

    override val deviceServices : StateFlow<List<DeviceService>> = bleGattService.deviceServices

    override val connectedDevice : StateFlow<ScannedDevice?> = bleGattService.connectedDevice

    override val setting : StateFlow<Setting?> = bleGattService.setting

    override suspend fun scanLeDevice() {
        bleScanService.scanLeDevice()
    }

    override fun stopLeScan() {
        bleScanService.stopLeScan()
    }

    override fun connect(scannedDevice: ScannedDevice) {
        bleGattService.connectToDevice(scannedDevice)
    }

    override fun close() {
        bleGattService.close()
    }

    override fun writeBytes(uuid: String, values: ByteArray) {
        bleGattService.writeBytes(uuid, values)
    }
}