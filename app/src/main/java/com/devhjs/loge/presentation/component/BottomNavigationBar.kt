package com.devhjs.loge.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.devhjs.loge.R
import com.devhjs.loge.core.routing.BottomNavItem
import com.devhjs.loge.core.routing.MainRoute
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun BottomNavigationBar(
    selectedRoute: String?,
    onItemClicked: (String) -> Unit,
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            route = MainRoute.Home,
            label = "홈",
            icon = painterResource(R.drawable.ic_home_outlined)
        ),
        BottomNavItem(
            route = MainRoute.Stat,
            label = "통계",
            icon = painterResource(R.drawable.ic_stat_outlined)
        ),
        BottomNavItem(
            route = MainRoute.Setting,
            label = "설정",
            icon = painterResource(R.drawable.setting_outlined)
        )
    )

    NavigationBar(
        containerColor = AppColors.cardBackground
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route.route,
                onClick = { onItemClicked(item.route.route) },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.iconPrimary,
                    selectedTextColor = AppColors.iconPrimary,
                    unselectedIconColor = AppColors.labelTextColor,
                    unselectedTextColor = AppColors.labelTextColor,
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}
