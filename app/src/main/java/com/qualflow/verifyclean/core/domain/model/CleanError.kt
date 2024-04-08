package com.qualflow.verifyclean.core.domain.model

enum class CleanError(
    val displayName: String,
    val errorCode: Int
) {
    UNKNOWN_ERROR(displayName = "Unknown error", errorCode = -1),
    NO_ERROR(displayName = "No error", errorCode = 0),
    POWER_OUTAGE_RECOVERY(displayName = "Power Outage recovery", errorCode = 1),
    CONDUCTIVITY_HIGH(displayName = "Conductivity High", errorCode = 2),
    CONDUCTIVITY_LOW(displayName = "Conductivity Low", errorCode = 3),
    TEMPERATURE_HIGH(displayName = "Temperature High", errorCode = 4),
    TEMPERATURE_LOW(displayName = "Temperature Low", errorCode = 5),
    NO_DETERGENT(displayName = "No Detergent", errorCode = 6),

    /**
     * For now, These errors are not supported by client app. Saved for future
     */

    /*NO_DETERGENT_TIMEOUT(displayName = "No Detergent Timeout", errorCode = 7),
    STEP_TIMEOUT(displayName = "Step Timeout", errorCode = 8),
    NO_FOB(displayName = "No FOB", errorCode = 9),
    VOLUME_LIMIT_BEFORE_SENSOR(displayName = "Volume Limit Before Sensor", errorCode = 10),
    HIGH_LEVEL_TRIGGERED(displayName = "High Level Triggered", errorCode = 11),
    TIMEOUT_TRIGGERED(displayName = "Timeout Triggered", errorCode = 12),
    VOL_LIMIT_TRIGGERED(displayName = "Vol Limit Triggered", errorCode = 13),*/


    NO_FLOW_ERROR(displayName = "No Flow Error", errorCode = 14),
    LID_OPEN_ERROR(displayName = "Lid Open Error", errorCode = 15)
}

fun getCleanError(errorCode: Int): CleanError {
    return when (errorCode) {
        CleanError.NO_ERROR.errorCode -> CleanError.NO_ERROR
        CleanError.POWER_OUTAGE_RECOVERY.errorCode -> CleanError.POWER_OUTAGE_RECOVERY
        CleanError.CONDUCTIVITY_HIGH.errorCode -> CleanError.CONDUCTIVITY_HIGH
        CleanError.CONDUCTIVITY_LOW.errorCode -> CleanError.CONDUCTIVITY_LOW
        CleanError.TEMPERATURE_HIGH.errorCode -> CleanError.TEMPERATURE_HIGH
        CleanError.TEMPERATURE_LOW.errorCode -> CleanError.TEMPERATURE_LOW
        CleanError.NO_DETERGENT.errorCode -> CleanError.NO_DETERGENT
        CleanError.NO_FLOW_ERROR.errorCode -> CleanError.NO_FLOW_ERROR
        CleanError.LID_OPEN_ERROR.errorCode -> CleanError.LID_OPEN_ERROR
        else -> CleanError.UNKNOWN_ERROR
    }
}

fun CleanError.isError() =
    this != CleanError.NO_ERROR