package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

/**
 * Log.e 앱의 공통 상단 바로, 2단 레이아웃(Top + Bottom)을 지원합니다.
 * HomeAppBar를 리팩토링하여 StatScreen 등에서도 사용할 수 있도록 범용성을 높였습니다.
 *
 * @param title 타이틀 텍스트
 * @param titleIcon 타이틀 좌측 아이콘 (없으면 null)
 * @param titleStyle 타이틀 텍스트 스타일 (기본값: Pretendard.Header1)
 * @param actions 우측 상단 액션 버튼들
 * @param bottomContent 하단 영역 컨텐츠 (옵션)
 */
@Composable
fun LogETopBar(
    modifier: Modifier = Modifier,
    title: String,
    titleIcon: Int? = null,
    titleStyle: TextStyle = AppTextStyles.Pretendard.Header1,
    actions: @Composable RowScope.() -> Unit = {},
    bottomContent: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.cardBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 상단 영역 (뒤로가기/아이콘/타이틀 + 액션)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 타이틀 아이콘
                    if (titleIcon != null) {
                        Icon(
                            painter = painterResource(id = titleIcon),
                            tint = AppColors.iconPrimary,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    // 타이틀
                    Text(
                        text = title,
                        style = AppTextStyles.JetBrain.Header1.copy(color = AppColors.white,),
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // 액션 버튼 영역
                Row(verticalAlignment = Alignment.CenterVertically) {
                    actions()
                }
            }

            // 하단 영역 (옵션)
            if (bottomContent != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    bottomContent()
                }
            }
        }
    }
}
