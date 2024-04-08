package com.qualflow.verifyclean.core.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.MaterialTheme
import com.qualflow.verifyclean.presentation.icons.CleanIcon
import com.qualflow.verifyclean.presentation.icons.CleanIconDefaults
import com.qualflow.verifyclean.presentation.theme.Green

sealed class CleanStatus(
    val ordinal: Int,
    val displayName: String
) {

    data object None : CleanStatus(
        ordinal = -1,
        displayName = "Unknown"
    )

    data object Idle : CleanStatus(
        ordinal = 0,
        displayName = "Idle"
    )

    data object Running : CleanStatus(
        ordinal = 1,
        displayName = "Running"
    )

    data object Paused : CleanStatus(
        ordinal = 2,
        displayName = "Paused"
    )

    data object Complete : CleanStatus(
        ordinal = 3,
        displayName = "Completed"
    )

    data class Error(val errors: CleanError) : CleanStatus(
        ordinal = 4,
        displayName = "Error"
    )

    data object Pending : CleanStatus(
        ordinal = 5,
        displayName = "Pending"
    )
}

fun getCleanStatus(statusCode: Int, errorCode: Int): CleanStatus {
    val error = getCleanError(errorCode)

    return when(statusCode) {
        0 -> CleanStatus.Idle
        1 -> CleanStatus.Running
        2 -> {
            //In some hardware clean status is returned as paused state.
            // checking if paused and has error considering it as error
            if (error.isError())
                CleanStatus.Error(error)
            else
                CleanStatus.Paused
        }
        3 -> CleanStatus.Complete
        4 -> CleanStatus.Error(error)
        5 -> CleanStatus.Pending
        else -> CleanStatus.None
    }
}

fun CleanStatus.toCleanIcon(): CleanIcon {
    return when(this) {
        CleanStatus.Idle -> CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.idle)
        CleanStatus.Running -> CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.cleaning_outline)
        CleanStatus.Complete -> CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.cleaned_outline)
        is CleanStatus.Error -> CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.error_outlined)
        CleanStatus.None -> CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.disconnected)
        CleanStatus.Paused -> CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.disconnected)
        CleanStatus.Pending -> CleanIcon.DrawableResourceCleanIcon(CleanIconDefaults.disconnected)
    }
}

@Composable
fun CleanStatus.toColor(): Color {
    return when(this) {
        CleanStatus.Idle -> MaterialTheme.colors.secondary
        CleanStatus.Running -> MaterialTheme.colors.primary
        CleanStatus.Complete -> Green
        is CleanStatus.Error -> MaterialTheme.colors.error
        CleanStatus.None -> Color.Transparent
        CleanStatus.Paused -> MaterialTheme.colors.secondary
        CleanStatus.Pending -> MaterialTheme.colors.secondary
    }
}
