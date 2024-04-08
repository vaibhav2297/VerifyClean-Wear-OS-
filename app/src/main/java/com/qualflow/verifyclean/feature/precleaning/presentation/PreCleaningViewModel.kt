package com.qualflow.verifyclean.feature.precleaning.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualflow.verifyclean.core.ble.data.mock.MockCleaning
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.feature.connection.presentation.ConnectionUiState
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
class PreCleaningViewModel @Inject constructor(
    private val bleRepository: IBLERepository,
    private val mockCleaning: MockCleaning
) : ViewModel() {

    private val _connectionState = bleRepository.connectionState
    private val _setting = bleRepository.setting
    private val _connectedDevice = bleRepository.connectedDevice

    /*To check if clean initialisation is in progress*/
    private val _isCleaningInitialising = MutableStateFlow(false)

    private val _isSettingLoaded = MutableStateFlow(false)

    val uiState = combine(
        _setting,
        _connectionState,
        _connectedDevice,
        _isCleaningInitialising,
        _isSettingLoaded
    ) { setting, connectionState, connectedDevice, isInitialising, isLoaded ->
        PreCleaningUiState(
            connectedDevice = connectedDevice,
            isCleaningInitialising = isInitialising,
            connectionState = connectionState,
            settingLoading = setting,
            isSettingLoaded = isLoaded
        )
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            PreCleaningUiState()
        )

    fun onEvent(event: PreCleaningUiEvent) {
        when (event) {
            is PreCleaningUiEvent.OnStartClean -> startClean()
            is PreCleaningUiEvent.OnCleaningStarted -> onCleanStarted()
        }
    }

    private fun startClean() {
        viewModelScope.launch {
            mockCleaning.apply {
                _isCleaningInitialising.value = true
                loadTime()
                startClean {
                    _isCleaningInitialising.value = false
                    _isSettingLoaded.value = true
                }
            }
        }
    }

    private fun onCleanStarted() {
        _isSettingLoaded.value = false
    }
}