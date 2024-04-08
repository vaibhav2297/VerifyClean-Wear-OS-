package com.qualflow.verifyclean.core.domain.model

import com.qualflow.verifyclean.core.utils.retrieveHardwareId

enum class HardwareType(
    val hardwareId: String,
    val displayName: String
) {
    NONE(
        hardwareId = "-1",
        displayName = "Unknown"
    ),
    VORTEX(
        hardwareId = "005",
        displayName = "Vortex"
    ),
    VERX(
        hardwareId = "007",
        displayName = "Verx"
    ),
    PLCS(
        hardwareId = "008",
        displayName = "PLCs"
    ),
    VORTEX_I(
        hardwareId = "009",
        displayName = "Vortex-I"
    ),
    VERX_LITE(
        hardwareId = "010",
        displayName = "Verx-Lite"
    ),
    VORTEX_N(
        hardwareId = "011",
        displayName = "Vortex-N"
    )
}

object HardwareTypeDefaults{

    fun getHardwareType(serialNumber: String): HardwareType {
        val hardwareId = retrieveHardwareId(serialNumber)
        return when (hardwareId) {
            HardwareType.VORTEX.hardwareId -> HardwareType.VORTEX
            HardwareType.VERX.hardwareId -> HardwareType.VERX
            HardwareType.PLCS.hardwareId -> HardwareType.PLCS
            HardwareType.VORTEX_I.hardwareId -> HardwareType.VORTEX_I
            HardwareType.VERX_LITE.hardwareId -> HardwareType.VERX_LITE
            HardwareType.VORTEX_N.hardwareId -> HardwareType.VORTEX_N
            else -> HardwareType.NONE
        }
    }
}