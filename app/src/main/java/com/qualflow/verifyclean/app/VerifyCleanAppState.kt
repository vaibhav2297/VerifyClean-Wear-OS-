package com.qualflow.verifyclean.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.qualflow.verifyclean.navigation.VerifyCleanNavDestination
import com.qualflow.verifyclean.navigation.VerifyCleanTopLevelDestination

@Composable
fun rememberVerifyCleanAppState(
    navController: NavHostController = rememberSwipeDismissableNavController()
): VerifyCleanAppState {
    return remember(navController) { VerifyCleanAppState(navController) }
}

@Stable
class VerifyCleanAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigate(destination: VerifyCleanNavDestination, route: String? = null) {
        if (destination is VerifyCleanTopLevelDestination) {
            navController.navigate(route ?: destination.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                val startDestination = navController.graph.findStartDestination()
                popUpTo(startDestination.id) {
                    inclusive = true
                }
                // Avoid multiple copies of the same destination when
                // re selecting the same item
                launchSingleTop = true
            }
        } else {
            navController.navigate(route ?: destination.route)
        }
    }

    fun onBackClick(): Boolean = navController.popBackStack()
}