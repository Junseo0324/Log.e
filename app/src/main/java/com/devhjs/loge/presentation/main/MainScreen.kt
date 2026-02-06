package com.devhjs.loge.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.loge.presentation.component.BottomNavigationBar
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun MainScreen(
    selectedRoute: String?,
    onBottomNavSelected: (String) -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        containerColor = AppColors.background,
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = selectedRoute,
                onItemClicked = onBottomNavSelected
            )
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
