package com.devhjs.loge.presentation.util

import androidx.compose.ui.graphics.Color
import com.devhjs.loge.R
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.presentation.designsystem.AppColors

/**
 * EmotionType에 대한 UI 관련 확장 프로퍼티
 * 색상 및 아이콘 리소스 매핑
 */

val EmotionType.color: Color
    get() = when (this) {
        EmotionType.FULFILLMENT -> AppColors.primary
        EmotionType.SATISFACTION -> AppColors.blue
        EmotionType.NORMAL -> AppColors.amber
        EmotionType.DIFFICULTY -> AppColors.purple
        EmotionType.FRUSTRATION -> AppColors.red
    }

val EmotionType.iconResId: Int
    get() = when (this) {
        EmotionType.FULFILLMENT -> R.drawable.ic_fulfillment
        EmotionType.SATISFACTION -> R.drawable.ic_satisfaction
        EmotionType.NORMAL -> R.drawable.ic_normal
        EmotionType.DIFFICULTY -> R.drawable.ic_difficulty
        EmotionType.FRUSTRATION -> R.drawable.ic_frustration
    }
