package com.qualflow.verifyclean.core.domain.model

enum class CleanType(val cleanId: Int) {
    //SOAK(cleanId = 0),
    NONE(cleanId = -1),
    VORTEX(cleanId = 1),
    VERX_AUTO(cleanId = 3),
    VERX_MANUAL(cleanId = 4),
    PLCS_AUTO(cleanId = 5),
    PLCS_MANUAL(cleanId = 6),
    VORTEXI_AUTO(cleanId = 7),
    VORTEXI_MANUAL(cleanId = 8),
    VERXLITE_AUTO(cleanId = 9),
    VORTEXN_AUTO(cleanId = 10),
    VORTEXN_MANUAL(cleanId = 11)
}

fun getCleanType(cleanId: Int): CleanType {
    return when (cleanId) {
        // SOAK(cleanId = 0), // Uncomment if you want to include SOAK
        CleanType.VORTEX.cleanId -> CleanType.VORTEX
        CleanType.VERX_AUTO.cleanId -> CleanType.VERX_AUTO
        CleanType.VERX_MANUAL.cleanId -> CleanType.VERX_MANUAL
        CleanType.PLCS_AUTO.cleanId -> CleanType.PLCS_AUTO
        CleanType.PLCS_MANUAL.cleanId -> CleanType.PLCS_MANUAL
        CleanType.VORTEXI_AUTO.cleanId -> CleanType.VORTEXI_AUTO
        CleanType.VORTEXI_MANUAL.cleanId -> CleanType.VORTEXI_MANUAL
        CleanType.VERXLITE_AUTO.cleanId -> CleanType.VERXLITE_AUTO
        CleanType.VORTEXN_AUTO.cleanId -> CleanType.VORTEXN_AUTO
        CleanType.VORTEXN_MANUAL.cleanId -> CleanType.VORTEXN_MANUAL
        else -> CleanType.NONE
    }
}
