package com.qualflow.verifyclean.core.domain.model

import kotlinx.serialization.Serializable

@Serializable

data class RunSetting(
    val cleanProcessType: Int = -1,
    val numberOfSteps: Int = 0,
    val flowMeterCalibration: Int = 0,
    val flowRateAverage: Int = 0,
    val conductivityAverage: Int = 0,
    val tempAverage: Int = 0,
    val errorClearVolume: Float = 0f,
    val tempMaxWater: Float = 0f,
    val tempMinWater: Float = 0f,
    val flowRateScalingLEDTarget: Float = 0f,
    val flowRateScalingLEDMax: Float = 0f,
    val flowRateScalingLEDMin: Float = 0f,
    val soakOnTime: Float = 0f,
    val soakOffTime: Float = 0f,
    val FOBTime: Float = 0f,
    val detergentErrorDetectVol: Int = 0,
    val stepSettings: MutableList<StepSetting> = mutableListOf()
) : Setting() {
    override fun toString(): String {
        return """
            RunSettings => 
                cleanProcessType: $cleanProcessType,
                numberOfSteps: $numberOfSteps,
                flowMeterCalibration: $flowMeterCalibration,
                flowRateAverage: $flowRateAverage,
                conductivityAverage: $conductivityAverage,
                tempAverage: $tempAverage,
                errorClearVolume: $errorClearVolume,
                tempMaxWater: $tempMaxWater,
                tempMinWater: $tempMinWater,
                flowRateScalingLEDTarget: $flowRateScalingLEDTarget,
                flowRateScalingLEDMax: $flowRateScalingLEDMax,
                flowRateScalingLEDMin: $flowRateScalingLEDMin,
                soakOnTime: $soakOnTime,
                soakOffTime: $soakOffTime,
                FOBTime: $FOBTime,
                detergentErrorDetectVol: $detergentErrorDetectVol,
                stepSettings: $stepSettings
        """.trimIndent()
    }
}
