package com.qualflow.verifyclean.core.ble.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.ParcelUuid
import android.util.Log
import com.qualflow.verifyclean.core.ble.domain.model.BleScanEvent
import com.qualflow.verifyclean.core.ble.domain.model.DeviceService
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.ble.domain.model.toScannedDevice
import com.qualflow.verifyclean.core.utils.BLEConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@SuppressLint("MissingPermission")
class BLEScanService @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter
) : IBLEScanService {

    companion object {
        const val SCAN_PERIOD: Long = 10000
        const val TAG = "BLEScanService"
        const val HARDWARE_ADDRESS = "DF:7F:99:16:A9:69"
    }

    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

    private var isScanning = false

    private val scannedDevices = mutableListOf<ScannedDevice>()

    private val _event: MutableStateFlow<BleScanEvent> by lazy {
        MutableStateFlow(BleScanEvent())
    }
    override val scanEvent = _event.asStateFlow()

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private val scanFilter = ScanFilter
        .Builder()
        .setServiceUuid(
            ParcelUuid.fromString(BLEConstants.Service.CLEANING_SERVICE)
        )
        .build()

    private fun isBluetoothEnabled() = bluetoothAdapter.isEnabled

    override suspend fun scanLeDevice() {
        try {
            if (!isScanning) {
                // Removing already existing device list
                _event.update { it.copy(scannedDevice = emptyList()) }
                startLeScan()
                delay(SCAN_PERIOD)
                stopLeScan()
            } else {
                stopLeScan()
            }
        } catch (e: Exception) {
            if (isScanning) stopLeScan()
            Log.e(TAG, "scanLeDevice :: exception : ${e.message}")
        }
    }

    private fun startLeScan() {
        try {
            if (isBluetoothEnabled()) {
                bluetoothLeScanner.startScan(listOf(scanFilter), scanSettings, leScanCallback)
                isScanning = true
                _event.update {
                    it.copy(
                        isScanning = true,
                        error = null
                    )
                }

            } else {
                Log.e(TAG, "startLeScan :: Bluetooth is not enabled")
                _event.update {
                    it.copy(
                        isScanning = false,
                        error = "Bluetooth is not enabled"
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "startLeScan :: exception : ${e.message}")
            _event.update {
                it.copy(
                    isScanning = false,
                    error = e.message
                )
            }
        }
    }

    override fun stopLeScan() {
        try {
            if (isBluetoothEnabled())
                bluetoothLeScanner.stopScan(leScanCallback)
        } catch (e: Exception) {
            Log.e(TAG, "stopLeScan :: exception : ${e.message}")
            _event.update {
                it.copy(
                    error = e.message
                )
            }
        } finally {
            isScanning = false
            _event.update {
                it.copy(
                    isScanning = false
                )
            }
        }
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)

            Log.d(
                TAG, "onScanResult :: callbackType : $callbackType : \n" +
                        "device name : ${result?.device?.name} \n" +
                        "device address : ${result?.device?.address} \n"
            )

            result?.let { scanResult ->
                val scannedDevice = scanResult.toScannedDevice()

                //adding scan result to list if not added before
                val presentDevice = scannedDevices.find { it.address == result.device?.address }
                if (presentDevice == null) {
                    scannedDevices.add(scannedDevice)

                    _event.update {
                        it.copy(
                            scannedDevice = scannedDevices
                        )
                    }
                }
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d(TAG, "onScanFailed :: error : $errorCode")

            //if scan is already started
            if (errorCode == 1) {
                stopLeScan()
                startLeScan()
            }
        }
    }

}