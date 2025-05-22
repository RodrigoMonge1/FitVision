package com.dapm.fitvision.navigation

sealed class AppScreens(val route: String) {
    object WelcomeScreen : AppScreens("welcome_screen")
    object SelectSexScreen : AppScreens("select_sex_screen")
    object CaptureScreen : AppScreens("capture_screen")
    object LoadingScreen : AppScreens("loading_screen/{tipo}") {
        fun createRoute(tipo: String) = "loading_screen/$tipo"
    }
    object ResultScreen : AppScreens("result_screen/{tipo}") {
        fun createRoute(tipo: String) = "result_screen/$tipo"
    }
    object ExerciseScreen: AppScreens("exercise_screen/{tipo}") {
        fun createRoute(tipo: String) = "exercise_screen/$tipo"
    }
}