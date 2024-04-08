package com.qualflow.verifyclean.presentation.icons

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.qualflow.verifyclean.R

/**
 * Pay Sync icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object CleanIconDefaults {
    // Default Icons


    // Drawable Resources
    val connectivity = R.drawable.ic_connectivity
    val bluetooth = R.drawable.ic_bluetooth
    val cleaned_outline = R.drawable.ic_cleaned_outlined
    val cleaning_filled = R.drawable.ic_cleaning_filled
    val cleaning_outline = R.drawable.ic_cleaning_outlined
    val cross = R.drawable.ic_cross
    val disconnected = R.drawable.ic_disconnected
    val error_outlined = R.drawable.ic_error_outlined
    val play = R.drawable.ic_play
    val search = R.drawable.ic_search
    val tick = R.drawable.ic_tick
    val idle = R.drawable.ic_idle
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class CleanIcon {
    data class ImageVectorCleanIcon(val imageVector: ImageVector) : CleanIcon()
    data class DrawableResourceCleanIcon(@DrawableRes val id: Int) : CleanIcon()
}
