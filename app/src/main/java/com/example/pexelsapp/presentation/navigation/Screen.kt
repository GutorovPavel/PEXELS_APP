package com.example.pexelsapp.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object Home: Screen("home_screen")
    data object Detail: Screen("detail_screen")
    data object Bookmarks: Screen("bookmarks_screen")
}