package com.qualflow.verifyclean.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.qualflow.verifyclean.R
import com.qualflow.verifyclean.core.utils.VerticalSpacer
import com.qualflow.verifyclean.presentation.icons.CleanIcon
import com.qualflow.verifyclean.presentation.icons.CleanIconDefaults

@Composable
fun DisconnectedIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CleanIcon(
            modifier = Modifier.size(16.dp),
            cleanIcon = CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.disconnected),
            tint = MaterialTheme.colors.onSurfaceVariant
        )

        VerticalSpacer(2.dp)

        Text(
            text = stringResource(R.string.disconnected),
            style = MaterialTheme.typography.caption3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onSurfaceVariant
        )
    }
}

@Composable
fun DisconnectLabel(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.disconnect_label),
            style = MaterialTheme.typography.caption1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurfaceVariant
        )
    }
}