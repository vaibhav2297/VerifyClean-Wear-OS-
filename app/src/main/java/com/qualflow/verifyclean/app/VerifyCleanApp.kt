package com.qualflow.verifyclean.app

import androidx.compose.runtime.Composable
import com.qualflow.verifyclean.navigation.VerifyCleanNavHost
import com.qualflow.verifyclean.presentation.theme.VerifyCleanTheme

@Composable
fun VerifyCleanApp(
    appState: VerifyCleanAppState = rememberVerifyCleanAppState()
) {
    VerifyCleanTheme {
        VerifyCleanNavHost(
            onBackClick = appState::onBackClick,
            navController = appState.navController,
            onNavigateToDestination = appState::navigate
        )
    }
}