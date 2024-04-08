package com.qualflow.verifyclean.feature.dashboard.model

import com.qualflow.verifyclean.feature.cleaning.navigation.CleaningDestination
import com.qualflow.verifyclean.feature.connection.navigation.ConnectionDestination
import com.qualflow.verifyclean.feature.precleaning.navigation.PreCleaningDestination
import com.qualflow.verifyclean.navigation.InvalidDestinationException
import com.qualflow.verifyclean.navigation.VerifyCleanNavDestination
import com.qualflow.verifyclean.presentation.icons.CleanIcon
import com.qualflow.verifyclean.presentation.icons.CleanIconDefaults

data class DashboardItem(
    val id: Int,
    val name: String,
    val cleanIcon: CleanIcon
)


object DashboardItemDefaults {

    val dashboardItems = listOf(
        DashboardItem(
            id = 0,
            name = "Connectivity",
            cleanIcon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.connectivity)
        ),
        DashboardItem(
            id = 1,
            name = "Cleaning",
            cleanIcon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.cleaning_filled)
        )
    )

    /**
     * Returns the destination based on the dashboard item
     * @return [VerifyCleanNavDestination]
     * @throws [InvalidDestinationException]
     */
    fun DashboardItem.toItemNavDestination(): VerifyCleanNavDestination {
        return when(id) {
            0 -> ConnectionDestination
            1 -> PreCleaningDestination
            else -> throw InvalidDestinationException()
        }
    }
}
