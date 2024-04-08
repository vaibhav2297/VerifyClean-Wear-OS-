package com.qualflow.verifyclean.core.utils

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.wear.compose.foundation.lazy.ScalingLazyListItemScope
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import com.qualflow.verifyclean.navigation.VerifyCleanNavDestination

typealias VoidCallback = () -> Unit
typealias ValueChanged<T> = (T) -> Unit

typealias VoidComposable = @Composable () -> Unit
typealias PaddingComposable = @Composable (PaddingValues) -> Unit
typealias BoxScopeComposable = @Composable BoxScope.() -> Unit
typealias RowScopeComposable = @Composable RowScope.() -> Unit
typealias ColumnScopeComposable = @Composable ColumnScope.() -> Unit
typealias ScalingLazyListScopeComposable = @Composable ScalingLazyListScope.() -> Unit
typealias ScalingLazyListItemScopeComposable = @Composable ScalingLazyListItemScope.() -> Unit

typealias NavigationCallback = (destination: VerifyCleanNavDestination, route: String?) -> Unit


