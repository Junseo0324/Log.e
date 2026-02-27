package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.designsystem.LogETheme

@Composable
fun SegmentedProgressBar(
    maxSteps: Int,
    currentStep: Float,
    activeColor: Color,
    inactiveColor: Color = LogETheme.colors.placeholderTextColor,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(maxSteps) { index ->
            val isActive = (index + 1) <= currentStep + 0.5f

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(if (isActive) activeColor else inactiveColor)
            )
        }
    }
}