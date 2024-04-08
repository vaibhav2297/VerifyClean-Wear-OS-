package com.qualflow.verifyclean.feature.dashboard.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import com.qualflow.verifyclean.core.ble.domain.model.isConnected
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.core.utils.ValueChanged
import com.qualflow.verifyclean.feature.dashboard.model.DashboardItem
import com.qualflow.verifyclean.feature.dashboard.model.DashboardItemDefaults
import com.qualflow.verifyclean.feature.dashboard.model.DashboardItemDefaults.toItemNavDestination
import com.qualflow.verifyclean.presentation.components.CleanChip
import com.qualflow.verifyclean.presentation.components.CleanScaffold
import com.qualflow.verifyclean.presentation.components.DisconnectedIndicator
import com.qualflow.verifyclean.presentation.components.HardwareInformation


@Composable
fun DashboardRoute(
    onNavigateToDestination: NavigationCallback,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardScreen(
        uiState = uiState,
        onNavigateToDestination = onNavigateToDestination
    )
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    uiState: DashboardUiState,
    onNavigateToDestination: NavigationCallback
) {
    CleanScaffold(
       modifier = modifier,
        topSlot = {
            if (uiState.connectionState.isConnected())
                HardwareInformation(serialNumber = uiState.connectedDevice?.deviceName ?: "unknown")
            else
                DisconnectedIndicator()
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = DashboardItemDefaults.dashboardItems,
                key = { item -> item.id }
            ) {item ->
                DashboardItems(dashboardItem = item) { dashboard ->
                    try {
                        val destination = dashboard.toItemNavDestination()
                        onNavigateToDestination(destination, null)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardItems(
    modifier: Modifier = Modifier,
    dashboardItem: DashboardItem,
    onClick: ValueChanged<DashboardItem>
) {
    CleanChip(
        modifier = modifier,
        text = dashboardItem.name,
        cleanIcon = dashboardItem.cleanIcon
    ) {
        onClick(dashboardItem)
    }
}