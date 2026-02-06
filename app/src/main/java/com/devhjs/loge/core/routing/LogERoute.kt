package com.devhjs.loge.core.routing

import androidx.annotation.DrawableRes
import com.devhjs.loge.R

sealed class LogERoute(
    val route: String,
    val title: String? = null,
    @DrawableRes val selectedIcon: Int? = null,
    @DrawableRes val unselectedIcon: Int? = null
) {
    data object Home : LogERoute("home", "홈", R.drawable.ic_home_filled, R.drawable.ic_home_outlined)
    data object Stat : LogERoute("stat", "통계", R.drawable.ic_stat_filled, R.drawable.ic_stat_outlined)
    data object Setting : LogERoute("setting", "설정", R.drawable.setting_filled, R.drawable.setting_outlined)
    data object Detail : LogERoute("detail")
    data object Write : LogERoute("write")
}
