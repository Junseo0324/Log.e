package com.devhjs.loge.presentation.main


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devhjs.loge.core.routing.MainNavGraph
import kotlinx.coroutines.launch


@Composable
fun MainScreenRoot(
    onNavigateToOnboarding: () -> Unit
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    MainScreen(
        selectedRoute = currentRoute,
        onBottomNavSelected = { route ->
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        snackbarHostState = snackbarHostState
    ) { modifier ->
        MainNavGraph(
            navController = navController,
            modifier = modifier,
            onNavigateToOnboarding = onNavigateToOnboarding,
            onShowSnackbar = { message ->
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        )
    }
}
