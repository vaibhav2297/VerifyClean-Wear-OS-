package com.qualflow.verifyclean.feature.precleaning.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.qualflow.verifyclean.R
import com.qualflow.verifyclean.core.ble.domain.model.isConnected
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.core.utils.ValueChanged
import com.qualflow.verifyclean.core.utils.VerticalSpacer
import com.qualflow.verifyclean.core.utils.VoidCallback
import com.qualflow.verifyclean.feature.cleaning.navigation.CleaningDestination
import com.qualflow.verifyclean.presentation.components.CleanIconButton
import com.qualflow.verifyclean.presentation.components.CleanScaffold
import com.qualflow.verifyclean.presentation.components.CleanTopSlotInformation
import com.qualflow.verifyclean.presentation.components.DisconnectLabel
import com.qualflow.verifyclean.presentation.components.DisconnectedIndicator
import com.qualflow.verifyclean.presentation.components.HardwareInformation
import com.qualflow.verifyclean.presentation.icons.CleanIcon
import com.qualflow.verifyclean.presentation.icons.CleanIconDefaults

@Composable
fun PreCleaningRoute(
    onNavigateToDestination: NavigationCallback,
    viewModel: PreCleaningViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PreCleaningScreen(
        uiState = uiState,
        onNavigateToDestination = onNavigateToDestination,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun PreCleaningScreen(
    modifier: Modifier = Modifier,
    uiState: PreCleaningUiState,
    onNavigateToDestination: NavigationCallback,
    onEvent: ValueChanged<PreCleaningUiEvent>
) {

    if (uiState.isSettingLoaded) {
        onEvent(
            PreCleaningUiEvent.OnCleaningStarted
        )

        onNavigateToDestination(CleaningDestination, null)
    }

    CleanScaffold(
        modifier = modifier,
        topSlot = {
            CleanTopSlotInformation(
                connectionState = uiState.connectionState,
                connectedDevice = uiState.connectedDevice
            )
        }
    ) {
        if (uiState.connectionState.isConnected()) {
            CleanSection(
                isInitialising = uiState.isCleaningInitialising,
                onStartClean = {
                    onEvent(
                        PreCleaningUiEvent.OnStartClean
                    )
                }
            )
        } else {
            DisconnectLabel()
        }
    }
}

@Composable
private fun CleanSection(
    modifier: Modifier = Modifier,
    isInitialising: Boolean,
    onStartClean: VoidCallback
) {
    val information = if (isInitialising) stringResource(R.string.initialising)
    else stringResource(R.string.start_clean)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CleanButton(
            isInitialising = isInitialising,
            onClick = onStartClean
        )

        Text(
            text = information,
            style = MaterialTheme.typography.caption1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CleanButton(
    modifier: Modifier = Modifier,
    isInitialising: Boolean,
    onClick: VoidCallback
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isInitialising) {
            CleanIconButton(
                modifier = modifier.size(64.dp),
                onClick = { }
            ) {
                CircularProgressIndicator()
            }
        } else {
            CleanIconButton(
                modifier = modifier.size(52.dp),
                icon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.play),
                onClick = onClick
            )
        }
    }
}