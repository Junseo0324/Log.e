package com.devhjs.loge.presentation.util

import androidx.compose.ui.graphics.Color
import com.devhjs.loge.R
import com.devhjs.loge.domain.model.EmotionType
import androidx.compose.runtime.Composable
import com.devhjs.loge.presentation.designsystem.LogETheme

/**
 * EmotionType에 대한 UI 관련 확장 프로퍼티
 * 색상 및 아이콘 리소스 매핑
 */

@get:Composable
val EmotionType.color: Color
    get() = when (this) {
        EmotionType.FULFILLMENT -> LogETheme.colors.primary
        EmotionType.SATISFACTION -> LogETheme.colors.blue
        EmotionType.NORMAL -> LogETheme.colors.amber
        EmotionType.DIFFICULTY -> LogETheme.colors.purple
        EmotionType.FRUSTRATION -> LogETheme.colors.red
    }

val EmotionType.iconResId: Int
    get() = when (this) {
        EmotionType.FULFILLMENT -> R.drawable.ic_fulfillment
        EmotionType.SATISFACTION -> R.drawable.ic_satisfaction
        EmotionType.NORMAL -> R.drawable.ic_normal
        EmotionType.DIFFICULTY -> R.drawable.ic_difficulty
        EmotionType.FRUSTRATION -> R.drawable.ic_frustration
    }
