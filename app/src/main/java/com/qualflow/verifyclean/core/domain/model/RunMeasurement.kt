package com.qualflow.verifyclean.core.domain.model

data class RunMeasurement(
    val runNumber: Int,
    val cleanType: CleanType,
    val numberOfSteps: Int,
    val cleanStartTime: Long,
    val cleanEndTime: Long?,
    val numberOfErrors: Int
) : Setting() {
    override fun toString(): String {
        return """
            RunMeasurement =>
                runNumber: $runNumber,
                cleanType: ${cleanType.name},
                numberOfSteps: $numberOfSteps,
                cleanStartTime: $cleanStartTime,
                cleanEndTime: $cleanEndTime,
                numberOfErrors: $numberOfErrors
        """.trimIndent()
    }
}
