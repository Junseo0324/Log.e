package com.devhjs.loge.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devhjs.loge.presentation.component.HomeAppBar
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize().background(AppColors.background)
    ) {
        HomeAppBar()

    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()

}