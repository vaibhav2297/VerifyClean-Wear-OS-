package com.qualflow.verifyclean.feature.dashboard.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.wear.compose.navigation.composable
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.feature.dashboard.presentation.DashboardRoute
import com.qualflow.verifyclean.navigation.VerifyCleanNavDestination
import com.qualflow.verifyclean.navigation.VerifyCleanTopLevelDestination

object DashboardDestination : VerifyCleanTopLevelDestination {
    override val route: String = "dashboardRoute"
    override val destination: String = "dashboardDestination"
}

fun NavGraphBuilder.dashboardGraph(
    onNavigateToDestination: NavigationCallback,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = DashboardDestination.route,
        startDestination = DashboardDestination.destination,
    ) {
        composable(
            route = DashboardDestination.destination
        ) {
            DashboardRoute(
                onNavigateToDestination = onNavigateToDestination
            )
        }
        nestedGraphs()
    }
}