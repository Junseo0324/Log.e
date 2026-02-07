package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

/**
 * 프로젝트 내에서 공통으로 사용되는 상단 앱바 컴포넌트 (Box 기반 중앙 정렬 버전)
 *
 * @param title 앱바 중앙에 표시될 제목
 * @param titleIcon 제목 왼쪽에 표시될 아이콘 리소스 ID (null 가능)
 * @param onBackClick 뒤로 가기 버튼 클릭 시 호출될 콜백
 * @param modifier Modifier
 * @param actions 앱바 우측에 배치될 추가 작업들 (수정, 삭제 버튼 등)
 */
@Composable
fun CustomAppBar(
    title: String,
    titleIcon: Int ,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(AppColors.cardBackground)
            .padding(horizontal = 4.dp)
    ) {
        // 좌측 뒤로 가기 버튼
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = AppColors.contentTextColor
            )
        }

        // 중앙 타이틀 영역
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = titleIcon),
                contentDescription = null,
                tint = AppColors.iconPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = AppTextStyles.Pretendard.Header1,
                color = AppColors.white
            )
        }

        // 우측 액션 버튼 영역 (CenterEnd)
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }
}

@Preview
@Composable
private fun DetailAppBarPreview() {
    CustomAppBar(
        title = "로그 상세",
        titleIcon = R.drawable.ic_detail,
        onBackClick = {},
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit",
                    tint = AppColors.contentTextColor
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete",
                    tint = AppColors.red
                )
            }
        }
    )
}

@Preview
@Composable
private fun WriteAppBarPreview() {
    CustomAppBar(
        title = "새 로그",
        titleIcon = R.drawable.ic_code,
        onBackClick = {},
        actions = {
            CustomButton(
                modifier = Modifier
                    .width(80.dp)
                    .padding(end = 8.dp),
                backgroundColor = AppColors.primary,
                icon = R.drawable.ic_save,
                text = "저장",
                contentColor = AppColors.black,
                onClick = { }
            )
        }
    )
}