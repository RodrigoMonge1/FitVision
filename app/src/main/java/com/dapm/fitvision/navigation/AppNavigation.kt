package com.dapm.fitvision.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dapm.fitvision.screens.CaptureScreen
import com.dapm.fitvision.screens.LoadingScreen
import com.dapm.fitvision.screens.ResultScreen
import com.dapm.fitvision.screens.SelectSexScreen
import com.dapm.fitvision.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.WelcomeScreen.route){
        composable(AppScreens.WelcomeScreen.route){
            WelcomeScreen(navController)
        }
        composable(AppScreens.SelectSexScreen.route){
            SelectSexScreen(navController)
        }
        composable(AppScreens.CaptureScreen.route){
            CaptureScreen(navController)
        }
        composable(AppScreens.LoadingScreen.route){
            LoadingScreen(navController)
        }
        composable(AppScreens.ResultScreen.route){
            ResultScreen()
        }
    }
}