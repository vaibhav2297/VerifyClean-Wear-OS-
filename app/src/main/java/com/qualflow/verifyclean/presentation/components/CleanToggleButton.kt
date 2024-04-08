package com.qualflow.verifyclean.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ToggleButton
import androidx.wear.compose.material.ToggleButtonColors
import androidx.wear.compose.material.ToggleButtonDefaults
import com.qualflow.verifyclean.core.utils.ValueChanged
import com.qualflow.verifyclean.presentation.icons.CleanIcon

object CleanToggleButtonDefaults {

    val extraSmallButtonSize = 32.dp

    val smallButtonSize = 42.dp

    val defaultButtonSize = 52.dp

    val largeButtonSize = 60.dp

    @Composable
    fun defaultToggleColor() = ToggleButtonDefaults.toggleButtonColors(
        checkedBackgroundColor = MaterialTheme.colors.primary,
        checkedContentColor = MaterialTheme.colors.onPrimary,
        uncheckedBackgroundColor = MaterialTheme.colors.surface,
        uncheckedContentColor = MaterialTheme.colors.onSurface
    )
}

@Composable
fun CleanToggleButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    icon: CleanIcon,
    toggleColors: ToggleButtonColors = CleanToggleButtonDefaults.defaultToggleColor(),
    onCheckedChanged: ValueChanged<Boolean>
) {

    ToggleButton(
        modifier = modifier.size(CleanToggleButtonDefaults.defaultButtonSize),
        checked = checked,
        onCheckedChange = onCheckedChanged,
        colors = toggleColors
    ) {
        CleanIcon(
            modifier = Modifier.size(16.dp),
            cleanIcon = icon,
            tint = LocalContentColor.current
        )
    }
}