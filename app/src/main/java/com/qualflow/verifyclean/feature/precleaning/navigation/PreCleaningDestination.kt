package com.qualflow.verifyclean.feature.precleaning.navigation


import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.navigation.composable
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.feature.precleaning.presentation.PreCleaningRoute
import com.qualflow.verifyclean.navigation.VerifyCleanNavDestination

object PreCleaningDestination : VerifyCleanNavDestination {
    override val route: String = "precleaningRoute"
    override val destination: String = "precleaningDestination"
}

fun NavGraphBuilder.precleaningGraph(
    onNavigateToDestination: NavigationCallback
) {
    composable(
        route = PreCleaningDestination.route
    ) {
        PreCleaningRoute(
            onNavigateToDestination = onNavigateToDestination
        )
    }
}