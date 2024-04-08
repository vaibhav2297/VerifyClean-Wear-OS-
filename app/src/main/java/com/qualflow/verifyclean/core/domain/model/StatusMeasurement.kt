package com.qualflow.verifyclean.core.domain.model


/**
 * Status Measurement : Gives the information about current state of cleaning process of current step
 */
data class StatusMeasurement(
    val processType: CleanType,
    val processTime: Long,
    val currentStepNo: Int,
    val currentProcessStepType: Int,
    val currentProcessStepTime: Int,
    val cleanStatus: CleanStatus,
    val errorCode: CleanError,
    val liquidVolCurrent: Float = 0f,
    val currentFlowRate: Float = 0f,
    val flowRatePercentageTarget: Int = 0,
    val currentConductivityAvg: Float = 0f,
    val currentTempAvg: Float = 0f,
) : Setting() {
    override fun toString(): String {
        return """
            StatusMeasurement =>
                processType: ${processType.name},
                processTime: $processTime,
                currentStepNo: $currentStepNo,
                currentProcessStepType: $currentProcessStepType,
                currentProcessStepTime: $currentProcessStepTime,
                cleanStatus: ${cleanStatus.displayName},
                errorCode: $errorCode,
                liquidVolCurrent: $liquidVolCurrent,
                currentFlowRate: $currentFlowRate,
                flowRatePercentageTarget: $flowRatePercentageTarget,
                currentConductivityAvg: $currentConductivityAvg,
                currentTempAvg: $currentTempAvg
        """.trimIndent()
    }
}