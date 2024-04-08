package com.qualflow.verifyclean.core.domain.model

data class StepError(
    val stepNumber: Int,
    val cleanError: CleanError
) : Setting() {
    override fun toString(): String {
        return """
            StepError =>
                stepNumber: $stepNumber,
                cleanError: ${cleanError.displayName}
        """.trimIndent()
    }
}

data class ErrorMeasurement(
    val stepErrors: List<StepError>
) : Setting()
