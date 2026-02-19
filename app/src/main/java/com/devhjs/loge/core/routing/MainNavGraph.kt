package com.devhjs.loge.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devhjs.loge.presentation.detail.DetailScreenRoot
import com.devhjs.loge.presentation.feedback.FeedbackScreenRoot
import com.devhjs.loge.presentation.home.HomeScreenRoot
import com.devhjs.loge.presentation.license.LicensesScreen
import com.devhjs.loge.presentation.profile.ProfileScreenRoot
import com.devhjs.loge.presentation.setting.SettingScreenRoot
import com.devhjs.loge.presentation.stat.StatScreenRoot
import com.devhjs.loge.presentation.write.WriteScreenRoot

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onShowSnackbar: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home.route,
        modifier = modifier
    ) {
        composable(MainRoute.Home.route) {
            HomeScreenRoot(
                onNavigateToDetail = { logId ->
                    navController.navigate(MainRoute.Detail.createRoute(logId))
                },
                onNavigateToWrite = {
                    navController.navigate(MainRoute.Write.route)
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(MainRoute.Stat.route) { StatScreenRoot() }
        composable(MainRoute.Setting.route) {
            SettingScreenRoot(
                onNavigateToLicenses = {
                    navController.navigate(MainRoute.Licenses.route)
                },
                onNavigateToProfileEdit = {
                    navController.navigate(MainRoute.ProfileEdit.route)
                },
                onNavigateToFeedback = {
                    navController.navigate(MainRoute.Feedback.route)
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(MainRoute.Licenses.route) {
            LicensesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(MainRoute.ProfileEdit.route) {
            ProfileScreenRoot(
                onBackClick = { navController.popBackStack() },
                onSubmitSuccess = { message ->
                    onShowSnackbar(message)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = MainRoute.Detail.route,
            arguments = listOf(
                navArgument("logId") { type = NavType.LongType }
            )
        ) {
            DetailScreenRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { logId ->
                    navController.navigate(MainRoute.Write.createRoute(logId))
                }
            )
        }
        composable(
            route = MainRoute.Write.route,
            arguments = listOf(
                navArgument("logId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            WriteScreenRoot(
                onBackClick = { navController.navigateUp() },
                onSubmitSuccess = { message ->
                    onShowSnackbar(message)
                    navController.popBackStack()
                }
            )
        }
        composable(MainRoute.Feedback.route) {
            FeedbackScreenRoot(
                onBackClick = { navController.popBackStack() },
                onSubmitSuccess = { message ->
                    onShowSnackbar(message)
                    navController.popBackStack()
                }
            )
        }
    }
}
