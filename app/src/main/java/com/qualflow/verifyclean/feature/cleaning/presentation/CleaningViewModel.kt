package com.qualflow.verifyclean.feature.cleaning.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualflow.verifyclean.core.ble.data.mock.MockCleaning
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.domain.model.CleanStatus
import com.qualflow.verifyclean.core.domain.model.RunMeasurement
import com.qualflow.verifyclean.core.domain.model.Setting
import com.qualflow.verifyclean.core.domain.model.StatusMeasurement
import com.qualflow.verifyclean.core.domain.model.StepMeasurement
import com.qualflow.verifyclean.feature.precleaning.presentation.PreCleaningUiState
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
class CleaningViewModel @Inject constructor(
    private val bleRepository: IBLERepository,
    private val mockCleaning: MockCleaning
) : ViewModel() {

    private val _connectedDevice = bleRepository.connectedDevice
    private val _connectionState = bleRepository.connectionState
    private val _setting = bleRepository.setting

    private val _runMeasurement = MutableStateFlow<RunMeasurement?>(null)
    private val _stepMeasurement = MutableStateFlow<StepMeasurement?>(null)
    private val _statusMeasurement = MutableStateFlow<StatusMeasurement?>(null)
    private val _isCleanFinish = MutableStateFlow<Boolean>(false)

    init {
        startProcessStatusPolling()
    }

    val uiState = combine(
        _setting,
        _connectionState,
        _connectedDevice,
        _isCleanFinish
    ) { setting, connectionState, device, iscleanFinish ->

        setting?.let { processSetting(it) }

        CleaningUiState(
            connectionState = connectionState,
            connectedDevice = device,
            currentStep = _statusMeasurement.value?.currentStepNo ?: 0,
            //Hardcoded amount
            totalSteps = 15,
            cleanStatus = _statusMeasurement.value?.cleanStatus ?: CleanStatus.Idle,
            isCleanFinish = iscleanFinish
        )

    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            CleaningUiState()
        )

    fun onEvent(event: CleaningUiEvent) {
        when(event) {
            is CleaningUiEvent.OnCancelClean -> cancelClean()
            is CleaningUiEvent.OnCleanMeasurement -> getCleaningMeasurement()
        }
    }

    private fun startProcessStatusPolling() {
        viewModelScope.launch {
            mockCleaning.startProcessStatusPolling()
        }
    }

    private fun getCleaningMeasurement() {
        viewModelScope.launch {
            mockCleaning.getCleanMeasurement {
                _isCleanFinish.value = true
            }
        }
    }

    private fun processSetting(setting: Setting) {
        when(setting) {
            is StatusMeasurement -> {
                Log.d("Cleaning", "$setting")
                _statusMeasurement.value = setting
            }
            is RunMeasurement -> {
                Log.d("Cleaning", "$setting")
                _runMeasurement.value = setting
            }
            is StepMeasurement -> {
                Log.d("Cleaning", "$setting")
                _stepMeasurement.value = setting
            }
            else -> { }
        }
    }

    private fun cancelClean() {
        mockCleaning.cancelClean()
    }
}