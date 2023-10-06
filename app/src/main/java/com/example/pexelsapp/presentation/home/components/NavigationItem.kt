package com.example.pexelsapp.presentation.home.components

import com.example.pexelsapp.R
import com.example.pexelsapp.presentation.navigation.Screen

sealed class NavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String,
    val selectedIconNight: Int,
    ) {
    data object Home: NavigationItem(
        "home",
        R.drawable.home_button_active,
        R.drawable.home_button_inactive,
        Screen.Home.route,
        R.drawable.home_button_active_night
    )
    data object Bookmarks: NavigationItem(
        "bookmarks",
        R.drawable.bookmarks_button_active,
        R.drawable.bookmarks_inactive,
        Screen.Bookmarks.route,
        R.drawable.bookmarks_button_active,
    )
}