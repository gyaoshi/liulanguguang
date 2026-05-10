package com.liulanguguang.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.liulanguguang.data.UserDataStore
import com.liulanguguang.ui.screens.*
import com.liulanguguang.viewmodel.HomeViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object AdExperience : Screen("ad/{adId}") {
        fun createRoute(adId: Int) = "ad/$adId"
    }
    data object Achievement : Screen("achievement")
    data object Settings : Screen("settings")
}

@Composable
fun MainNavigation(
    userDataStore: UserDataStore,
    onDarkModeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val homeViewModel = remember { HomeViewModel(userDataStore) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(
                route = Screen.Home.route,
                enterTransition = { fadeIn() + slideInHorizontally() },
                exitTransition = { fadeOut() + slideOutHorizontally() }
            ) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onAdClick = { adId ->
                        navController.navigate(Screen.AdExperience.createRoute(adId))
                    },
                    onAchievementClick = {
                        navController.navigate(Screen.Achievement.route)
                    },
                    onSettingsClick = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }

            composable(
                route = Screen.AdExperience.route,
                arguments = listOf(navArgument("adId") { type = NavType.IntType }),
                enterTransition = { fadeIn() + slideInHorizontally { it } },
                exitTransition = { fadeOut() + slideOutHorizontally { -it } }
            ) { backStackEntry ->
                val adId = backStackEntry.arguments?.getInt("adId") ?: 1
                AdExperienceScreen(
                    adId = adId,
                    userDataStore = userDataStore,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.Achievement.route,
                enterTransition = { fadeIn() + slideInHorizontally { it } },
                exitTransition = { fadeOut() + slideOutHorizontally { -it } }
            ) {
                AchievementScreen(
                    userDataStore = userDataStore,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.Settings.route,
                enterTransition = { fadeIn() + slideInHorizontally { it } },
                exitTransition = { fadeOut() + slideOutHorizontally { -it } }
            ) {
                SettingsScreen(
                    userDataStore = userDataStore,
                    onBack = { navController.popBackStack() },
                    onDarkModeChange = onDarkModeChange
                )
            }
        }
    }
}
