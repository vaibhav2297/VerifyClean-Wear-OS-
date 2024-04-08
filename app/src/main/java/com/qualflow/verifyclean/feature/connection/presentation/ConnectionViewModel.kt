package com.qualflow.verifyclean.feature.connection.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualflow.verifyclean.core.ble.data.mock.MockCleaning
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.domain.model.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val bleRepository: IBLERepository,
    private val mockCleaning: MockCleaning
) : ViewModel() {

    init {
        observeBleSetting()
    }

    private val _services = bleRepository.deviceServices
    private val _connectionState = bleRepository.connectionState
    private val _scanEvent = bleRepository.bleScanEvent
    private val _connectedDevice = bleRepository.connectedDevice

    /*Device that use has selected to connect with it*/
    private val _selectedDevice = MutableStateFlow<ScannedDevice?>(null)

    private val _deviceDetails =
        combine(
            _selectedDevice,
            _services,
            _connectionState
        ) { selectedDevice, deviceServices, connectionState ->
            selectedDevice?.let {
                DeviceDetail(
                    device = it,
                    services = deviceServices,
                    connectionState = connectionState
                )
            }
        }

    val uiState = combine(
        _deviceDetails,
        _connectionState,
        _scanEvent
    ) { deviceDetail, connectionState, scanEvents ->
        ConnectionUiState(
            scannedDevices = scanEvents.scannedDevice,
            isScanning = scanEvents.isScanning,
            deviceDetail = deviceDetail,
            connectionState = connectionState,
            connectionError = scanEvents.error
        )
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ConnectionUiState()
        )

    fun onEvent(event: ConnectionUiEvent) {
        when (event) {
            is ConnectionUiEvent.OnConnectionEstablish -> {
                if (isDeviceAlreadyConnected(event.device))
                    close()
                else
                    connectToDevice(event.device)
            }

            is ConnectionUiEvent.OnStartScan -> scanLeDevices()
            is ConnectionUiEvent.OnStopScan -> stopLeScan()
            is ConnectionUiEvent.OnGetDeviceInfo -> getDeviceInfo()
        }
    }

    private fun isDeviceAlreadyConnected(scannedDevice: ScannedDevice): Boolean {
        return uiState.value.deviceDetail?.device?.address == scannedDevice.address
    }

    private fun scanLeDevices() {
        viewModelScope.launch {
            bleRepository.scanLeDevice()
        }
    }

    private fun stopLeScan() {
        viewModelScope.launch {
            bleRepository.stopLeScan()
        }
    }

    private fun connectToDevice(scannedDevice: ScannedDevice) {
        viewModelScope.launch {
            _selectedDevice.value = scannedDevice
            //stopping scan before making connection
            bleRepository.stopLeScan()
            bleRepository.connect(scannedDevice)
        }
    }

    private fun close() {
        viewModelScope.launch {
            _selectedDevice.value = null
            bleRepository.close()
        }
    }

    private fun observeBleSetting() {
        viewModelScope.launch {
            bleRepository.setting.collect { s ->
                s?.let { setting ->
                    val time = setting as Time
                    Log.d("Settings", "$time")
                }
            }
        }
    }

    private fun getDeviceInfo() {
        viewModelScope.launch {

        }
    }
}