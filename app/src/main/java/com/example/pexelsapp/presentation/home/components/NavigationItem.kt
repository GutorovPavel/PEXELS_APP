package com.example.pexelsapp.presentation.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.pexelsapp.R
import com.example.pexelsapp.presentation.navigation.Screen

sealed class NavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String,
    val selectedIconNight: Int,
    ) {
    object Home: NavigationItem(
        "home",
        R.drawable.home_button_active,
        R.drawable.home_button_inactive,
        Screen.Home.route,
        R.drawable.home_button_active_night
    )
    object Bookmarks: NavigationItem(
        "bookmarks",
        R.drawable.bookmarks_button_active,
        R.drawable.bookmarks_inactive,
        Screen.Bookmarks.route,
        R.drawable.bookmarks_button_active,
    )
}