package com.qualflow.verifyclean.feature.cleaning.navigation


import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.navigation.composable
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.feature.cleaning.presentation.CleaningRoute
import com.qualflow.verifyclean.navigation.VerifyCleanNavDestination

object CleaningDestination : VerifyCleanNavDestination {
    override val route: String = "cleaningRoute"
    override val destination: String = "cleaningDestination"
}

fun NavGraphBuilder.cleaningGraph(
    onNavigateToDestination: NavigationCallback
) {
    composable(
        route = CleaningDestination.route
    ) {
        CleaningRoute(
            onNavigateToDestination = onNavigateToDestination
        )
    }
}