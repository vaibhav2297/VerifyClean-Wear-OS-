package com.qualflow.verifyclean.feature.cleaning.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.qualflow.verifyclean.R
import com.qualflow.verifyclean.core.domain.model.CleanError
import com.qualflow.verifyclean.core.domain.model.CleanStatus
import com.qualflow.verifyclean.core.domain.model.toCleanIcon
import com.qualflow.verifyclean.core.domain.model.toColor
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.core.utils.ValueChanged
import com.qualflow.verifyclean.core.utils.VerticalSpacer
import com.qualflow.verifyclean.core.utils.VoidCallback
import com.qualflow.verifyclean.feature.dashboard.navigation.DashboardDestination
import com.qualflow.verifyclean.presentation.components.CleanIcon
import com.qualflow.verifyclean.presentation.components.CleanIconButton
import com.qualflow.verifyclean.presentation.components.CleanIconButtonDefaults
import com.qualflow.verifyclean.presentation.components.CleanScaffold
import com.qualflow.verifyclean.presentation.components.CleanTopSlotInformation
import com.qualflow.verifyclean.presentation.icons.CleanIcon
import com.qualflow.verifyclean.presentation.icons.CleanIconDefaults

@Composable
fun CleaningRoute(
    onNavigateToDestination: NavigationCallback,
    viewModel: CleaningViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CleaningScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateToDestination = onNavigateToDestination
    )
}

@Composable
fun CleaningScreen(
    modifier: Modifier = Modifier,
    uiState: CleaningUiState,
    onNavigateToDestination: NavigationCallback,
    onEvent: ValueChanged<CleaningUiEvent>
) {
    CleanScaffold(
        modifier = modifier,
        topSlot = {
            CleanTopSlotInformation(
                connectionState = uiState.connectionState,
                connectedDevice = uiState.connectedDevice,
                shouldShowHardwareType = false
            )
        },
        positionIndicator = {
            CleaningProgressIndicator(uiState = uiState)
        }
    ) {
        CleaningSection(
            uiState = uiState,
            onCancel = {
                onEvent(
                    CleaningUiEvent.OnCancelClean
                )
                onNavigateToDestination(DashboardDestination, null)
            }
        ) {
            onNavigateToDestination(DashboardDestination, null)
        }
    }
}

@Composable
fun CleaningProgressIndicator(
    modifier: Modifier = Modifier,
    uiState: CleaningUiState
) {
    val progress = if (uiState.currentStep > 0)
        (uiState.currentStep.toFloat() / uiState.totalSteps.toFloat())
    else 0f

    CircularProgressIndicator(
        modifier = modifier.fillMaxSize(),
        progress = progress,
        indicatorColor = uiState.cleanStatus.toColor()
    )
}

@Composable
private fun CleaningSection(
    modifier: Modifier = Modifier,
    uiState: CleaningUiState,
    onCancel: VoidCallback,
    onFinish: VoidCallback
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CleanIcon(
            modifier = Modifier.weight(1f),
            cleanIcon = uiState.cleanStatus.toCleanIcon(),
            tint = uiState.cleanStatus.toColor()
        )
        VerticalSpacer(height = 2.dp)

        when (uiState.cleanStatus) {
            is CleanStatus.Error -> {
                CleanErrorSection(
                    modifier = Modifier.weight(2f),
                    error = uiState.cleanStatus.errors,
                    onCancel = onCancel
                )
            }

            is CleanStatus.Complete -> {
                CleanFinishSection(
                    modifier = Modifier.weight(2f),
                    onFinish = onFinish
                )
            }

            else -> {
                CleanStepSection(
                    modifier = Modifier.weight(2f),
                    currentStep = uiState.currentStep,
                    totalStep = uiState.totalSteps,
                    onCancel = onCancel
                )
            }
        }
    }
}

@Composable
private fun CleanStepSection(
    modifier: Modifier = Modifier,
    currentStep: Int,
    totalStep: Int,
    onCancel: VoidCallback
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = modifier,
            text = "$currentStep/$totalStep",
            style = MaterialTheme.typography.display2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        VerticalSpacer(height = 2.dp)
        CancelCleanButton(onCancel = onCancel)
    }
}

@Composable
private fun CleanCompletedSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CircularProgressIndicator()
        VerticalSpacer(height = 2.dp)
        Text(
            modifier = modifier,
            text = stringResource(R.string.cleaning_up),
            style = MaterialTheme.typography.title3,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CleanFinishSection(
    modifier: Modifier = Modifier,
    onFinish: VoidCallback
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = modifier,
            text = stringResource(R.string.clean_finished),
            style = MaterialTheme.typography.title3,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(height = 2.dp)
        FinishCleanButton(onFinish = onFinish)
    }
}

@Composable
private fun CleanErrorSection(
    modifier: Modifier = Modifier,
    error: CleanError,
    onCancel: VoidCallback
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = modifier,
            text = error.displayName,
            style = MaterialTheme.typography.title3,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(height = 2.dp)
        CancelCleanButton(onCancel = onCancel)
    }
}

@Composable
private fun CancelCleanButton(
    modifier: Modifier = Modifier,
    onCancel: VoidCallback
) {
    CleanIconButton(
        modifier = modifier,
        colors = CleanIconButtonDefaults.enabledColor(),
        icon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.cross),
        onClick = onCancel
    )
}

@Composable
private fun FinishCleanButton(
    modifier: Modifier = Modifier,
    onFinish: VoidCallback
) {
    CleanIconButton(
        modifier = modifier,
        colors = CleanIconButtonDefaults.enabledColor(),
        icon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.tick),
        onClick = onFinish
    )
}