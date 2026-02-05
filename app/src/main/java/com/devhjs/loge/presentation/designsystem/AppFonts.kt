package com.devhjs.loge.presentation.designsystem

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.devhjs.loge.R

object AppFonts {
    val pretendard = FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold),
        Font(R.font.pretendard_bold, FontWeight.Bold)
    )

    val jetBrain = FontFamily(
        Font(R.font.jetbrains_mono_nl_regular, FontWeight.Normal),
        Font(R.font.jetbrains_mono_nl_bold, FontWeight.Bold)
    )
}