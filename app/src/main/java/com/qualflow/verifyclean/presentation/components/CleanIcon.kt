package com.qualflow.verifyclean.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qualflow.verifyclean.R
import com.qualflow.verifyclean.presentation.icons.CleanIcon


object CleanIconDefaults {
    val iconSize = 24.dp
}

@Composable
fun CleanIcon(
    modifier: Modifier = Modifier,
    cleanIcon: CleanIcon,
    contentDescription: String = stringResource(R.string.icon),
    tint: Color = LocalContentColor.current
) =
    when (cleanIcon) {
        is CleanIcon.ImageVectorCleanIcon -> {
            Icon(
                modifier = modifier
                    .size(CleanIconDefaults.iconSize),
                imageVector = cleanIcon.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }

        is CleanIcon.DrawableResourceCleanIcon -> {
            Icon(
                modifier = modifier
                    .size(CleanIconDefaults.iconSize),
                painter = painterResource(id = cleanIcon.id),
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }