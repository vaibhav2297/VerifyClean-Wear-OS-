package com.qualflow.verifyclean.core.ble.data.mock

import android.util.Log
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.domain.model.RunMeasurement
import com.qualflow.verifyclean.core.domain.model.RunSetting
import com.qualflow.verifyclean.core.domain.model.Setting
import com.qualflow.verifyclean.core.domain.model.StatusMeasurement
import com.qualflow.verifyclean.core.domain.model.StepMeasurement
import com.qualflow.verifyclean.core.domain.model.StepSetting
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.VoidCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalUnsignedTypes::class)
class MockCleaning @Inject constructor(
    private val bleRepository: IBLERepository
) {

    private val _services = bleRepository.deviceServices
    private val _setting = bleRepository.setting

    private var _shouldPolling = false

    init {
        observeCleanResponse()
    }

    private fun observeCleanResponse() {
        CoroutineScope(Dispatchers.IO).launch {
            _setting.collect {
                it?.let { s -> processSetting(s) }
            }
        }
    }

    private fun processSetting(setting: Setting) {
        when(setting) {
            is RunSetting -> { Log.d(TAG, "Response :: Run setting") }
            is StepSetting -> { Log.d(TAG, "Response :: Step ${setting.stepNumber} setting") }
            is StatusMeasurement -> { Log.d(TAG, "Response :: Status Measurement") }
            is RunMeasurement -> { Log.d(TAG, "Response :: Run Measurement") }
            is StepMeasurement -> { Log.d(TAG, "Response :: Step Measurement") }
            else -> { Log.d(TAG, "Response :: Unknown setting") }
        }
    }

    suspend fun loadTime() {
        //write time
        writeTime()
        delay(DELAY)

        //read Time
        readTime()
        delay(DELAY)
    }

    suspend fun startClean(onSettingLoaded : VoidCallback) {
        //cancel clean
        writeCancel()
        delay(DELAY)

        //sending run setting
        writeRunSetting()
        delay(DELAY)

        //sending step settings
        writeStepSettings()
        delay(DELAY)

        onSettingLoaded()
    }

    fun cancelClean() {
        writeCancel()
    }

    suspend fun startProcessStatusPolling() {
        //getting process status
        _shouldPolling = true
        startProcessStatusTimer()
    }

    suspend fun getCleanMeasurement(onCleanFinish: VoidCallback) {
        stopProcessStatusPolling()

        //write run measurement
        writeRunMeasurement()
        delay(DELAY)

        //read step measurement
        writeStepMeasurements()

        //cancel clean
        writeCancel()
        delay(DELAY)

        onCleanFinish()
    }

    private fun getCleaningCharacteristic() = run {
        _services.value.let { svc ->
            svc.flatMap { it.characteristics }.find {
                it.uuid == BLEConstants.Characteristic.CLEANING_SERVICE_WRITE
            }
        }
    }

    private fun writeTime() {
        if (getCleaningCharacteristic() == null) {
            Log.d(TAG, "writeTime :: char is null")
        }
        getCleaningCharacteristic()?.let { char ->
            Log.d(TAG, "writeTime")
            bleRepository.writeBytes(char.uuid, MockCommand.writeTime)
        }
    }

    private fun readTime() {
        getCleaningCharacteristic()?.let { char ->
            Log.d(TAG, "readTime")
            bleRepository.writeBytes(char.uuid, MockCommand.readTime)
        }
    }

    private fun writeRunSetting() {
        getCleaningCharacteristic()?.let { char ->
            Log.d(TAG, "writeRunSetting")
            bleRepository.writeBytes(char.uuid, MockCommand.runSetting)
        }
    }

    private suspend fun writeStepSettings() {
        getCleaningCharacteristic()?.let { char ->
            for ((index, stepSetting) in MockCommand.stepSettings.withIndex()) {
                Log.d(TAG, "writeStepSettings :: step : ${index + 1}")
                bleRepository.writeBytes(char.uuid, stepSetting)
                delay(DELAY)
            }
        }
    }

    private suspend fun startProcessStatusTimer() {
        while (_shouldPolling) {
            writeProcessStatus()
            delay(5000L)
        }
    }

    private fun stopProcessStatusPolling() {
        _shouldPolling = false
    }

    private fun writeProcessStatus() {
        getCleaningCharacteristic()?.let { char ->
            Log.d(TAG, "writeProcessStatus")
            bleRepository.writeBytes(char.uuid, MockCommand.statusMeasurement)
        }
    }

    private fun writeRunMeasurement() {
        getCleaningCharacteristic()?.let { char ->
            Log.d(TAG, "writeRunMeasurement")
            bleRepository.writeBytes(char.uuid, MockCommand.runMeasurement)
        }
    }

    private suspend fun writeStepMeasurements() {
        getCleaningCharacteristic()?.let { char ->
            for (i in 1..MockCommand.stepSettings.size) {
                Log.d(TAG, "writeStepMeasurements :: step :$i")
                val stepMeasurement = MockCommand.stepMeasurement(i)
                bleRepository.writeBytes(char.uuid, stepMeasurement)
                delay(DELAY)
            }
        }
    }

    private fun writeCancel() {
        getCleaningCharacteristic()?.let { char ->
            Log.d(TAG, "writeCancel")
            bleRepository.writeBytes(char.uuid, MockCommand.cancel)
        }
    }

    companion object {
        const val TAG = "MockCleaning"
        const val DELAY = 500L
    }
}