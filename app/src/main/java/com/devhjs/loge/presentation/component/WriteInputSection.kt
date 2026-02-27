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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun WriteInputSection(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    minHeight: Dp,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = AppTextStyles.Pretendard.Label,
            color = LogETheme.colors.subTextColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight)
                .background(LogETheme.colors.cardBackground, RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = if (isFocused) LogETheme.colors.iconPrimary else LogETheme.colors.border,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 12.dp),
            contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = AppTextStyles.Pretendard.Body.copy(color = LogETheme.colors.titleTextColor),
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                cursorBrush = SolidColor(LogETheme.colors.white),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = AppTextStyles.Pretendard.Body.copy(color = LogETheme.colors.placeholderTextColor)
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