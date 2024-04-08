package com.qualflow.verifyclean.feature.connection.navigation


import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.navigation.composable
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.feature.connection.presentation.ConnectionRoute
import com.qualflow.verifyclean.navigation.VerifyCleanNavDestination

object ConnectionDestination : VerifyCleanNavDestination {
    override val route: String = "connectionRoute"
    override val destination: String = "connectionDestination"
}

fun NavGraphBuilder.connectionGraph(
    onNavigateToDestination: NavigationCallback
) {
    composable(
        route = ConnectionDestination.route
    ) {
        ConnectionRoute(
            onNavigateToDestination = onNavigateToDestination
        )
    }
}