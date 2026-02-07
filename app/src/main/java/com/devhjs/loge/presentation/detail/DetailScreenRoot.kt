package com.devhjs.loge.presentation.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.CustomAppBar
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun DetailScreenRoot(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "로그 상세",
                titleIcon = R.drawable.ic_detail,
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Edit",
                            tint = AppColors.contentTextColor
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Delete",
                            tint = AppColors.red
                        )
                    }
                }
            )
        },
        containerColor = AppColors.background
    ) { paddingValues ->
        DetailScreen(
            modifier = modifier.padding(paddingValues),
        )
    }
}