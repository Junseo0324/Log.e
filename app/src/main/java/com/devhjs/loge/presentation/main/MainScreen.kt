package com.devhjs.loge.presentation.main

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.loge.core.routing.MainRoute
import com.devhjs.loge.presentation.component.BottomNavigationBar
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun MainScreen(
    selectedRoute: String?,
    onBottomNavSelected: (String) -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        containerColor = AppColors.cardBackground,
        bottomBar = {
            val isBottomBarVisible = selectedRoute == MainRoute.Home.route ||
                    selectedRoute == MainRoute.Stat.route ||
                    selectedRoute == MainRoute.Setting.route

            if (isBottomBarVisible) {
                BottomNavigationBar(
                    selectedRoute = selectedRoute,
                    onItemClicked = onBottomNavSelected
                )
            }
        }
    ) { innerPadding ->
        content(
            Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}
