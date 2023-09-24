package com.example.pexelsapp.presentation.navigation

sealed class Screen(
    val route: String
) {
    object Home: Screen("home_screen")
    object Detail: Screen("detail_screen")
}
