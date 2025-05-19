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
        composable(AppScreens.CaptureScreen.route) {
            CaptureScreen(navController)
        }
        composable(AppScreens.LoadingScreen.route) {
            LoadingScreen(navController)
        }
        composable(
            route = AppScreens.ResultScreen.route,
            arguments = listOf(navArgument("tipo") { type = NavType.StringType })
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: "Desconocido"
            ResultScreen(somatotipo = tipo)
        }
    }
}
