package com.example.pexelsapp.presentation.navigation

sealed class Screen(
    val route: String
) {
    object Home: Screen("home_screen")
    object Detail: Screen("detail_screen")
    object Bookmarks: Screen("bookmarks_screen")
}

sealed class Graph(
    val route: String
) {
    object Root: Graph("root_graph")
    object Home: Graph("home_graph")
}
