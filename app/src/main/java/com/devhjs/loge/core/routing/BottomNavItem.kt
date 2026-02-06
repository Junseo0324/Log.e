package com.devhjs.loge.core.routing

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val route: MainRoute,
    val label: String,
    val icon: Painter
)
