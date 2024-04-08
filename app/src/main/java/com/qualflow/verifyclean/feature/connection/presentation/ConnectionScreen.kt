package com.qualflow.verifyclean.feature.connection.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.qualflow.verifyclean.R
import com.qualflow.verifyclean.core.ble.domain.model.DeviceDetail
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.ble.domain.model.isDisconnected
import com.qualflow.verifyclean.core.utils.NavigationCallback
import com.qualflow.verifyclean.core.utils.ValueChanged
import com.qualflow.verifyclean.core.utils.VerticalSpacer
import com.qualflow.verifyclean.presentation.components.CleanChip
import com.qualflow.verifyclean.presentation.components.CleanChipDefaults
import com.qualflow.verifyclean.presentation.components.CleanScaffold
import com.qualflow.verifyclean.presentation.components.CleanToggleButton
import com.qualflow.verifyclean.presentation.components.CleanToggleButtonDefaults
import com.qualflow.verifyclean.presentation.icons.CleanIcon
import com.qualflow.verifyclean.presentation.icons.CleanIconDefaults

@Composable
fun ConnectionRoute(
    onNavigateToDestination: NavigationCallback,
    viewModel: ConnectionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConnectionScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun ConnectionScreen(
    modifier: Modifier = Modifier,
    uiState: ConnectionUiState,
    onEvent: ValueChanged<ConnectionUiEvent>
) {
    CleanScaffold(
        modifier = modifier,
        topSlot = {
            ScanTopSlot(
                isScanning = uiState.isScanning,
                onEvent = onEvent
            )
        }
    ) {
        if (uiState.scannedDevices.isEmpty() && uiState.isScanning.not()) {
            NoDevicesAvailable()
        } else {
            ScannedDevices(
                devices = uiState.scannedDevices,
                connectedDevice = uiState.deviceDetail
            ) { selectedDevice ->
                onEvent(
                    ConnectionUiEvent.OnConnectionEstablish(selectedDevice)
                )
            }
        }
    }
}

@Composable
fun ScanTopSlot(
    modifier: Modifier = Modifier,
    isScanning: Boolean,
    onEvent: ValueChanged<ConnectionUiEvent>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CleanToggleButton(
            modifier = Modifier.size(CleanToggleButtonDefaults.smallButtonSize),
            checked = isScanning,
            icon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.search)
        ) { isScan ->
            Log.d("ToggleButton", "isScan:: $isScan")
            if (isScan) {
                onEvent(ConnectionUiEvent.OnStartScan)
            } else {
                onEvent(ConnectionUiEvent.OnStopScan)
            }
        }

        if (isScanning) {
            VerticalSpacer(6.dp)

            Text(
                text = stringResource(R.string.searching),
                style = MaterialTheme.typography.caption3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Composable
private fun ScannedDevices(
    modifier: Modifier = Modifier,
    devices: List<ScannedDevice>,
    connectedDevice: DeviceDetail?,
    onDeviceSelect: ValueChanged<ScannedDevice>
) {
    ScalingLazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = devices,
            key = { device -> device.address }
        ) { devices ->
            ScannedDevicesItem(
                scannedDevice = devices,
                connectedDevice = connectedDevice
            ) { selectedDevice ->
                onDeviceSelect(selectedDevice)
            }
        }
    }
}

@Composable
private fun ScannedDevicesItem(
    modifier: Modifier = Modifier,
    scannedDevice: ScannedDevice,
    connectedDevice: DeviceDetail?,
    onClick: ValueChanged<ScannedDevice>
) {
    //showing device address in case of name unavailability
    val displayName = scannedDevice.deviceName.ifEmpty { scannedDevice.address }
    var chipColor = CleanChipDefaults.defaultChipColors()
    var connectionLabel: String? = null

    connectedDevice?.run {
        if (device?.address == scannedDevice.address) {
            //active chip if device is connected, connecting or disconnecting
            if (!connectionState.isDisconnected())
                chipColor = CleanChipDefaults.activeChipColors()

            //not showing disconnected label
            if (!connectionState.isDisconnected())
                connectionLabel = connectionState.displayName
        }
    }

    CleanChip(
        modifier = modifier.fillMaxWidth(),
        text = displayName,
        secondaryText = connectionLabel,
        colors = chipColor,
        cleanIcon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.bluetooth),
        onClick = { onClick(scannedDevice) }
    )
}

@Composable
private fun NoDevicesAvailable(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_devices_available),
            style = MaterialTheme.typography.caption1,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}