package com.devhjs.loge.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devhjs.loge.presentation.detail.DetailScreenRoot
import com.devhjs.loge.presentation.home.HomeScreenRoot
import com.devhjs.loge.presentation.setting.SettingScreenRoot
import com.devhjs.loge.presentation.stat.StatScreenRoot
import com.devhjs.loge.presentation.write.WriteScreenRoot

@Composable
fun LogENavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LogERoute.Home.route,
        modifier = modifier
    ) {
        composable(LogERoute.Home.route) { HomeScreenRoot() }
        composable(LogERoute.Stat.route) { StatScreenRoot() }
        composable(LogERoute.Setting.route) { SettingScreenRoot() }
        composable(LogERoute.Detail.route) { DetailScreenRoot() }
        composable(LogERoute.Write.route) { WriteScreenRoot() }
    }
}
