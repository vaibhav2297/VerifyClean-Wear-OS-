package com.qualflow.verifyclean.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.core.utils.VoidCallback
import com.qualflow.verifyclean.feature.cleaning.navigation.CleaningDestination
import com.qualflow.verifyclean.feature.cleaning.navigation.cleaningGraph
import com.qualflow.verifyclean.feature.connection.navigation.connectionGraph
import com.qualflow.verifyclean.feature.dashboard.navigation.DashboardDestination
import com.qualflow.verifyclean.feature.dashboard.navigation.dashboardGraph
import com.qualflow.verifyclean.feature.precleaning.navigation.precleaningGraph
import com.qualflow.verifyclean.feature.precleaning.presentation.PreCleaningRoute

@Composable
fun VerifyCleanNavHost(
    onBackClick: VoidCallback,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToDestination: NavigationCallback,
    startDestination: String = DashboardDestination.route,
) {
    SwipeDismissableNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        dashboardGraph(
            onNavigateToDestination = onNavigateToDestination
        ) {
            connectionGraph(
                onNavigateToDestination = onNavigateToDestination
            )

            precleaningGraph(
                onNavigateToDestination = onNavigateToDestination
            )

            cleaningGraph(
                onNavigateToDestination = onNavigateToDestination
            )
        }
    }
}