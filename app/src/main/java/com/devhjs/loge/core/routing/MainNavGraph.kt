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
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home.route,
        modifier = modifier
    ) {
        composable(MainRoute.Home.route) {
            HomeScreenRoot(
                onNavigateToDetail = { logId ->
                    // TODO: logId를 Detail 화면에 전달하는 방식으로 수정 필요
                    navController.navigate(MainRoute.Detail.route)
                },
                onNavigateToWrite = {
                    navController.navigate(MainRoute.Write.route)
                }
            )
        }
        composable(MainRoute.Stat.route) { StatScreenRoot() }
        composable(MainRoute.Setting.route) { SettingScreenRoot() }
        composable(MainRoute.Detail.route) { DetailScreenRoot() }
        composable(MainRoute.Write.route) { WriteScreenRoot() }
    }
}
