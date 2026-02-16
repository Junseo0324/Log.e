package com.devhjs.loge.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.devhjs.loge.MainActivity
import com.devhjs.loge.R

@Composable
fun QuickStatsWidgetContent(
    totalTilCount: Int,
    monthlyTilCount: Int,
    avgDifficulty: Float,
    achievementRate: Int
) {
    // 위젯 전체 컨테이너
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .cornerRadius(10.dp)
            .padding(17.dp)
            .clickable(actionStartActivity<MainActivity>())
    ) {
        // 상단 헤더: 번개 아이콘 + "QUICK STATS" 타이틀
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.fillMaxWidth()
        ) {
            Image(
                provider = ImageProvider(R.drawable.ic_widget_bolt),
                contentDescription = null,
                modifier = GlanceModifier.size(16.dp)
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            Text(
                text = "QUICK STATS",
                style = TextStyle(
                    color = ColorProvider(Color(0xFFA1A1A1)),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(modifier = GlanceModifier.defaultWeight())

        // 하단 4개 지표 컬럼 (전체, 이번달, 평균난이도, 달성률)
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 전체 TIL 수
            StatColumn(
                iconRes = R.drawable.ic_widget_total,
                iconBgColor = Color(0x1A00BC7D),
                value = "$totalTilCount",
                label = "전체",
                modifier = GlanceModifier.defaultWeight()
            )
            // 이번달 TIL 수
            StatColumn(
                iconRes = R.drawable.ic_widget_monthly,
                iconBgColor = Color(0x1A00B8DB),
                value = "$monthlyTilCount",
                label = "이번달",
                modifier = GlanceModifier.defaultWeight()
            )
            // 평균 난이도
            StatColumn(
                iconRes = R.drawable.ic_difficulty,
                iconBgColor = Color(0x1AAD46FF),
                value = String.format("%.1f", avgDifficulty),
                label = "평균난이도",
                modifier = GlanceModifier.defaultWeight()
            )
            // 달성률
            StatColumn(
                iconRes = R.drawable.ic_widget_achievement,
                iconBgColor = Color(0x1AFF6900),
                value = "${achievementRate}%",
                label = "달성률",
                modifier = GlanceModifier.defaultWeight()
            )
        }
    }
}

/**
 * 개별 지표 컬럼 컴포넌트
 * 아이콘(배경 원) + 수치 + 라벨 세로 배치
 */
@Composable
private fun StatColumn(
    iconRes: Int,
    iconBgColor: Color,
    value: String,
    label: String,
    modifier: GlanceModifier = GlanceModifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // 아이콘 배경 (둥근 사각형)
        Box(
            contentAlignment = Alignment.Center,
            modifier = GlanceModifier
                .size(40.dp)
                .cornerRadius(10.dp)
                .background(iconBgColor)
        ) {
            Image(
                provider = ImageProvider(iconRes),
                contentDescription = null,
                modifier = GlanceModifier.size(20.dp)
            )
        }

        Spacer(modifier = GlanceModifier.height(8.dp))

        // 수치 값
        Text(
            text = value,
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = GlanceModifier.height(4.dp))

        // 라벨
        Text(
            text = label,
            style = TextStyle(
                color = ColorProvider(Color(0xFF737373)),
                fontSize = 12.sp
            )
        )
    }
}
