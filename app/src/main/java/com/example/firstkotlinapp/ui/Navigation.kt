package com.example.firstkotlinapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firstkotlinapp.ui.screens.CalendarScreen
import com.example.firstkotlinapp.ui.screens.HabitTrackerScreen
import com.example.firstkotlinapp.ui.screens.ToDoScreen



sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object ToDo : Screen("todo", "To-Do", Icons.AutoMirrored.Filled.List)
    object Habits : Screen("habits", "Habits", Icons.Default.Favorite)
    object Calendar : Screen("calendar", "Calendar", Icons.Default.DateRange)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.ToDo,
        Screen.Habits,
        Screen.Calendar,
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.ToDo.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.ToDo.route) { ToDoScreen() }
            composable(Screen.Habits.route) { HabitTrackerScreen() }
            composable(Screen.Calendar.route) { CalendarScreen() }
        }
    }
}
