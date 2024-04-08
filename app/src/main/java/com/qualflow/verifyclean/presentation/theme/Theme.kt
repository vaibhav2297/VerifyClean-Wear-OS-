package com.qualflow.verifyclean.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

@Composable
fun VerifyCleanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    /*For now only Dark mode is supported*/
    /*val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme*/

    MaterialTheme(
        colors = DarkColorScheme,
        typography = typography,
        content = content
    )
}

private val DarkColorScheme = Colors(
    primary = Blue,
    onPrimary = Black100,
    primaryVariant = BlueVariant,
    secondary = Yellow,
    onSecondary = Black100,
    secondaryVariant = YellowVariant,
    surface = Black100,
    onSurface = White,
    onSurfaceVariant = Grey,
    background = Black,
    onBackground = White,
    error = Red,
    onError = Black100
)