package com.qualflow.verifyclean.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StepSetting(
    val stepNumber: Int = 0,
    val stepType: Int = -1,
    val liquidTimeout1: Int = 0,
    val liquidTimeNextStep: Int = -1,
    val liquidVol: Float = 0f,
    val liquidVolNextStep: Int = -1,
    val inputButton: Int = -1,
    val inputNextStep: Int = -1,
    val maxConductivity: Float = 0f,
    val minConductivity: Float = 0f
) : Setting() {

    override fun toString(): String {
        return """
            StepSettings =>
                stepNumber: $stepNumber,
                stepType: $stepType,
                liquidTimeout1: $liquidTimeout1,
                liquidTimeNextStep: $liquidTimeNextStep,
                liquidVol: $liquidVol,
                liquidVolNextStep: $liquidVolNextStep,
                inputButton: $inputButton,
                inputNextStep: $inputNextStep,
                maxConductivity: $maxConductivity,
                minConductivity: $minConductivity
        """.trimIndent()
    }
}
