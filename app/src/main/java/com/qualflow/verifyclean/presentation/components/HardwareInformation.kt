package com.qualflow.verifyclean.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.qualflow.verifyclean.core.domain.model.HardwareTypeDefaults
import com.qualflow.verifyclean.core.utils.VerticalSpacer
import com.qualflow.verifyclean.core.utils.removeBrandName

/**
 * Composable used to display the essential information of connected hardware
 *
 * - Hardware Serial Number
 * - Hardware Type
 */
@Composable
fun HardwareInformation(
    modifier: Modifier = Modifier,
    serialNumber: String,
    shouldShowHardwareType: Boolean = true
) {
    val hardwareType by remember {
        mutableStateOf(HardwareTypeDefaults.getHardwareType(serialNumber))
    }

    val hardwareName by remember {
        mutableStateOf(removeBrandName(serialNumber))
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hardwareName,
            style = MaterialTheme.typography.caption2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (shouldShowHardwareType) {
            VerticalSpacer(2.dp)
            Text(
                text = hardwareType.displayName,
                style = MaterialTheme.typography.title3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}