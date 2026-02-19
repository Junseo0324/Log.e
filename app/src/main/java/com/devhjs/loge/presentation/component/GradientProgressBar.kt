package com.devhjs.loge.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun GradientProgressBar(
    progress: Float,
    gradient: Brush,
    trackColor: Color = AppColors.placeholderTextColor
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(CircleShape)
    ) {
        drawRoundRect(
            color = trackColor,
            cornerRadius = CornerRadius(size.height / 2, size.height / 2),
            size = size
        )
        drawRoundRect(
            brush = gradient,
            cornerRadius = CornerRadius(size.height / 2, size.height / 2),
            size = Size(width = size.width * progress, height = size.height)
        )
    }
}
