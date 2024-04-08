package com.qualflow.verifyclean.core.ble.data

import com.qualflow.verifyclean.core.ble.domain.model.BleScanEvent
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import kotlinx.coroutines.flow.StateFlow

interface IBLEScanService {

    val scanEvent: StateFlow<BleScanEvent>
    suspend fun scanLeDevice()
    fun stopLeScan()
}