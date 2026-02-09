package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun WriteInputSection(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    minHeight: Dp,
    singleLine: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = AppTextStyles.Pretendard.Label,
            color = AppColors.subTextColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight)
                .background(AppColors.cardBackground, RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = if (isFocused) AppColors.iconPrimary else AppColors.border,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 12.dp),
            contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = AppTextStyles.Pretendard.Body.copy(color = AppColors.titleTextColor),
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = AppTextStyles.Pretendard.Body.copy(color = AppColors.placeholderTextColor)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Preview
@Composable
private fun WriteInputSectionPreview() {
    WriteInputSection(
        label = "// 제목",
        placeholder = "오늘 무엇을 배웠나요?",
        value = "",
        onValueChange = {},
        minHeight = 44.dp
    )
}