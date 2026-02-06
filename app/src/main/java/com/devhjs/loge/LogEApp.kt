package com.devhjs.loge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devhjs.loge.core.routing.LogENavGraph
import com.devhjs.loge.core.routing.LogERoute

@Composable
fun LogEApp() {
    val navController = rememberNavController()

    val items = listOf(
        LogERoute.Home,
        LogERoute.Stat,
        LogERoute.Setting,
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            
            val isBottomBarVisible = currentDestination?.route in items.map { it.route }
            
            if (isBottomBarVisible) {
                 NavigationBar {
                    items.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        id = if (selected) screen.selectedIcon!! else screen.unselectedIcon!!
                                    ),
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title ?: "") },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LogENavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
