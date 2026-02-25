package com.devhjs.loge

import androidx.compose.runtime.Composable
import com.devhjs.loge.presentation.main.MainScreenRoot

@Composable
fun LogEApp(
    onNavigateToOnboarding: () -> Unit
) {
    MainScreenRoot(
        onNavigateToOnboarding = onNavigateToOnboarding
    )
}
