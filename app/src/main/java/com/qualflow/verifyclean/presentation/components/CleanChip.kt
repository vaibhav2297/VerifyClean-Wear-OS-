package com.qualflow.verifyclean.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipColors
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.qualflow.verifyclean.core.utils.VoidCallback
import com.qualflow.verifyclean.presentation.icons.CleanIcon

object CleanChipDefaults {

    @Composable
    fun defaultChipColors() = ChipDefaults.chipColors(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        iconColor = MaterialTheme.colors.onSurface
    )

    @Composable
    fun activeChipColors() = ChipDefaults.chipColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        iconColor = MaterialTheme.colors.onPrimary
    )
}

@Composable
fun CleanChip(
    modifier: Modifier = Modifier,
    text: String,
    secondaryText: String? = null,
    cleanIcon: CleanIcon,
    colors: ChipColors = CleanChipDefaults.defaultChipColors(),
    onClick: VoidCallback
) {
    Chip(
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.caption1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = LocalContentColor.current
            )
        },
        secondaryLabel = {
            if (!secondaryText.isNullOrEmpty()) {
                Text(
                    text = secondaryText,
                    style = MaterialTheme.typography.caption3,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = LocalContentColor.current
                )
            }
        },
        colors = colors,
        onClick = onClick,
        icon = {
            CleanIcon(
                cleanIcon = cleanIcon,
                tint = LocalContentColor.current
            )
        }
    )
}