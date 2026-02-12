package com.devhjs.loge.core.routing

sealed class MainRoute(val route: String) {
    data object Home : MainRoute("home")
    data object Stat : MainRoute("stat")
    data object Setting : MainRoute("setting")
    data object Detail : MainRoute("detail/{logId}") {
        fun createRoute(logId: Long) = "detail/$logId"
    }
    data object Write : MainRoute("write?logId={logId}") {
        fun createRoute(logId: Long? = null) = if (logId != null) "write?logId=$logId" else "write"
    }
    data object Licenses : MainRoute("licenses")
    data object ProfileEdit : MainRoute("profile_edit")
}
