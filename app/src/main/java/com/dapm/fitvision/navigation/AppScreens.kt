package com.dapm.fitvision.navigation

sealed class AppScreens(val route: String){
    object WelcomeScreen: AppScreens("welcome_screen")
    object SelectSexScreen: AppScreens("select_sex_screen")
    object CaptureScreen: AppScreens("capture_screen")
    object LoadingScreen: AppScreens("loading_screen")
    object ResultScreen: AppScreens("result_screen")
}