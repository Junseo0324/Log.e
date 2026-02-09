package com.devhjs.loge.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.InfoItem
import com.devhjs.loge.presentation.component.LogETopBar
import com.devhjs.loge.presentation.component.SectionHeader
import com.devhjs.loge.presentation.component.SettingActionItem
import com.devhjs.loge.presentation.component.SettingSectionContainer
import com.devhjs.loge.presentation.component.SettingToggleItem
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onNotificationToggle: (Boolean) -> Unit = {},
    isNotificationEnabled: Boolean = true,
    onAutoAnalysisToggle: (Boolean) -> Unit = {},
    isAutoAnalysisEnabled: Boolean = true,
    onExportClick: () -> Unit = {},
    onDeleteAllClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        LogETopBar(
            title = "설정",
            titleIcon = R.drawable.setting_outlined,
            bottomContent = {
                Text(
                    text = "// 앱 환경설정 및 데이터 관리",
                    color = AppColors.labelTextColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile
            SettingSectionContainer {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(AppColors.gradient1, AppColors.gradient2)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_normal),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Developer",
                            color = AppColors.titleTextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "dev@log-e.com",
                            color = AppColors.labelTextColor,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = null,
                        tint = AppColors.labelTextColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(iconRes = R.drawable.setting_outlined, title = "앱 설정")
            SettingSectionContainer {
                Column(modifier = Modifier.padding(16.dp)) {
                    SettingToggleItem(
                        iconRes = R.drawable.ic_time,
                        title = "알림",
                        subtitle = "학습 리마인더 받기",
                        checked = isNotificationEnabled,
                        onCheckedChange = onNotificationToggle
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingToggleItem(
                        iconRes = R.drawable.ic_stat_outlined,
                        title = "다크 모드",
                        subtitle = "현재는 다크 모드만 지원",
                        checked = true,
                        onCheckedChange = {},
                        enabled = false,
                        isReadOnly = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingToggleItem(
                        iconRes = R.drawable.ic_ai,
                        title = "자동 AI 분석",
                        subtitle = "로그 저장 시 자동 분석",
                        checked = isAutoAnalysisEnabled,
                        onCheckedChange = onAutoAnalysisToggle,
                        checkedColor = Color(0xFF404040)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(iconRes = R.drawable.ic_save, title = "데이터 관리")
            SettingSectionContainer {
                Column(modifier = Modifier.padding(16.dp)) {
                    SettingActionItem(
                        iconRes = R.drawable.ic_save,
                        title = "데이터 내보내기",
                        subtitle = "JSON 형식으로 저장",
                        onClick = onExportClick
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingActionItem(
                        iconRes = R.drawable.ic_delete,
                        title = "모든 데이터 삭제",
                        subtitle = "복구할 수 없습니다",
                        titleColor = AppColors.red,
                        onClick = onDeleteAllClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(iconRes = R.drawable.ic_detail, title = "앱 정보")
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.background(AppColors.cardBackground, RoundedCornerShape(10.dp)).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),

            ) {
                InfoItem(label = "버전", value = "v1.0.0")
                InfoItem(label = "빌드 날짜", value = "2024.02.08")

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.background, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "// Log.e - 개발자를 위한 TIL 기록 앱",
                            color = AppColors.primary,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "// 매일의 배움을 기록하고 AI로 성장을 분석하세요",
                            color = AppColors.labelTextColor,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Made with 💚 for developers",
                    color = AppColors.labelTextColor,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}




@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}