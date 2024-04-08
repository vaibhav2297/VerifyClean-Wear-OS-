package com.qualflow.verifyclean.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StepMeasurement(
    val stepNumber: Int = -1,
    val liquidTime: Int = 0,
    val liquidVolTotal: Float = 0f,
    val liquidFlowRateAvg: Float = 0f,
    val maxLiquidFlowRate: Float = 0f,
    val minLiquidFlowRate: Float = 0f,
    val maxConductivity: Float? = 0f,
    val minConductivity: Float? = 0f,
    val conductivityAvg: Float? = 0f,
    val tempAvg: Float = 0f,
    val maxTemp: Float = 0f,
    val minTemp: Float = 0f
) : Setting() {

    override fun toString(): String {
        return """
            StepMeasurement =>
                stepNumber: $stepNumber,
                liquidTime: $liquidTime,
                liquidVolTotal: ${liquidVolTotal},
                liquidFlowRateAvg: ${liquidFlowRateAvg},
                maxLiquidFlowRate: ${maxLiquidFlowRate},
                minLiquidFlowRate: ${minLiquidFlowRate},
                maxConductivity: ${maxConductivity},
                minConductivity: ${minConductivity},
                conductivityAvg: $conductivityAvg,
                tempAvg: $tempAvg,
                maxTemp: $maxTemp,
                minTemp: $minTemp
        """.trimIndent()
    }
}
