package com.qualflow.verifyclean.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonColors
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ToggleButton
import androidx.wear.compose.material.ToggleButtonColors
import androidx.wear.compose.material.ToggleButtonDefaults
import com.qualflow.verifyclean.core.utils.BoxScopeComposable
import com.qualflow.verifyclean.core.utils.ValueChanged
import com.qualflow.verifyclean.core.utils.VoidCallback
import com.qualflow.verifyclean.presentation.icons.CleanIcon

object CleanIconButtonDefaults {

    val extraSmallButtonSize = 32.dp

    val smallButtonSize = 42.dp

    val defaultButtonSize = 52.dp

    val largeButtonSize = 60.dp

    @Composable
    fun defaultColor() = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )

    @Composable
    fun enabledColor() = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    )
}

@Composable
fun CleanIconButton(
    modifier: Modifier = Modifier,
    icon: CleanIcon,
    enabled: Boolean = true,
    colors: ButtonColors = CleanIconButtonDefaults.defaultColor(),
    onClick: VoidCallback
) {
    Button(
        modifier = modifier.size(CleanToggleButtonDefaults.defaultButtonSize),
        colors = colors,
        enabled = enabled,
        onClick = onClick
    ) {
        CleanIcon(
            modifier = Modifier.size(24.dp),
            cleanIcon = icon,
            tint = LocalContentColor.current
        )
    }
}

@Composable
fun CleanIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = CleanIconButtonDefaults.defaultColor(),
    onClick: VoidCallback,
    content: BoxScopeComposable
) {
    Button(
        modifier = modifier.size(CleanToggleButtonDefaults.defaultButtonSize),
        colors = colors,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}