package com.devhjs.loge.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreenRoot(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit= {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit= {},
) {
    DetailScreen(
        onBackClick = onBackClick,
        onEditClick = onEditClick,
        onDeleteClick = onDeleteClick,
        modifier = modifier
    )
}