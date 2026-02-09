package com.devhjs.loge.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        composable(MainRoute.Home.route) { backStackEntry ->
            val savedStateHandle = backStackEntry.savedStateHandle
            val snackbarMessage = savedStateHandle.get<String>("snackbar_message")

            HomeScreenRoot(
                onNavigateToDetail = { logId ->
                    navController.navigate(MainRoute.Detail.createRoute(logId))
                },
                onNavigateToWrite = {
                    navController.navigate(MainRoute.Write.route)
                },
                snackbarMessage = snackbarMessage,
                onConsumeSnackbarMessage = {
                    savedStateHandle.remove<String>("snackbar_message")
                }
            )
        }
        composable(MainRoute.Stat.route) { StatScreenRoot() }
        composable(MainRoute.Setting.route) { SettingScreenRoot() }
        composable(
            route = MainRoute.Detail.route,
            arguments = listOf(
                navArgument("logId") { type = NavType.LongType }
            )
        ) {
            DetailScreenRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { logId -> /* TODO: Navigate to Edit with ID */ }
            )
        }
        composable(MainRoute.Write.route) {
            WriteScreenRoot(
                onBackClick = { navController.navigateUp() },
                onSubmitSuccess = { message ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("snackbar_message", message)
                    navController.popBackStack()
                }
            )
        }
    }
}
