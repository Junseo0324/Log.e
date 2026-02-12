package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIconRes: Int? = null,
    prefix: @Composable (() -> Unit)? = null,
    enabled: Boolean = true
) {
    Column(modifier = modifier) {
        // Label with Icon
        if (leadingIconRes != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = leadingIconRes),
                    contentDescription = null,
                    tint = AppColors.labelTextColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    style = AppTextStyles.JetBrain.Label.copy(
                        color = AppColors.labelTextColor,
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Input Field
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = AppTextStyles.JetBrain.Label.copy(
                color = if (enabled) AppColors.contentTextColor else AppColors.subTextColor,
                fontSize = 14.sp
            ),
            cursorBrush = SolidColor(AppColors.primary),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.cardInner, RoundedCornerShape(10.dp))
                        .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (prefix != null) {
                        prefix()
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = AppTextStyles.JetBrain.Label.copy(
                                    color = AppColors.subTextColor, 
                                    fontSize = 14.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    CustomTextField(
        value = "",
        onValueChange = {},
        label = "Label",
        placeholder = "Placeholder",
    )
}