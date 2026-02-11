package com.devhjs.loge.presentation.license

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.CustomAppBar
import com.devhjs.loge.presentation.designsystem.AppColors
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
fun LicensesScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "오픈소스 라이센스",
                titleIcon = R.drawable.ic_license,
                onBackClick = onBackClick
            )
        },
        containerColor = AppColors.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppColors.background)
        ) {
            LibrariesContainer(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview
@Composable
private fun LicenseScreenPreview() {
    LicensesScreen(onBackClick = {})
}
