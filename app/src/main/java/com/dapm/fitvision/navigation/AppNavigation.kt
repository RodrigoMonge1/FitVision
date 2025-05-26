package com.dapm.fitvision.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dapm.fitvision.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.WelcomeScreen.route) {
        composable(AppScreens.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(AppScreens.SelectSexScreen.route) {
            SelectSexScreen(navController)
        }
        composable(
            route = AppScreens.CaptureScreen.route,
            arguments = listOf(navArgument("sex") { type = NavType.StringType })
        ) { backStackEntry ->
            val sex = backStackEntry.arguments?.getString("sex") ?: "Desconocido"
            CaptureScreen(navController = navController, sex = sex)
        }
        composable(
            route = AppScreens.LoadingScreen.route,
            arguments = listOf(navArgument("tipo") { type = NavType.StringType })
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: "Desconocido"
            LoadingScreen(navController, tipo)
        }
        composable(
            route = AppScreens.ResultScreen.route,
            arguments = listOf(navArgument("tipo") { type = NavType.StringType })
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: "Desconocido"
            ResultScreen(navController,tipo)
        }
        composable(
            route = AppScreens.ExerciseScreen.route,
            arguments = listOf(navArgument("tipo") { type = NavType.StringType })
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: "Desconocido"
            ExerciseScreen(tipo)
        }
    }
}
