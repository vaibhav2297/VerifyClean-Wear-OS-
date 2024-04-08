package com.qualflow.verifyclean.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Scaffold
import com.qualflow.verifyclean.core.utils.ColumnScopeComposable
import com.qualflow.verifyclean.core.utils.VerticalSpacer
import com.qualflow.verifyclean.core.utils.VoidComposable

object CleanScaffoldDefaults {
    private val horizontalPadding = 2.dp
    private val verticalPadding = 10.dp

    @Composable
    fun defaultPadding() = PaddingValues(
        horizontal = horizontalPadding,
        vertical = verticalPadding
    )

    @Composable
    fun noPadding() = PaddingValues()
}

@Composable
fun CleanScaffold(
    modifier: Modifier = Modifier,
    topSlot: ColumnScopeComposable,
    positionIndicator: VoidComposable? = null,
    contentPadding: PaddingValues = CleanScaffoldDefaults.defaultPadding(),
    content: VoidComposable
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        positionIndicator = positionIndicator,
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                topSlot(this)

                VerticalSpacer(10.dp)

                content()
            }
        }
    )
}