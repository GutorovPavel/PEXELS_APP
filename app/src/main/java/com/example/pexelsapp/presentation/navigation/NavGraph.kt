package com.example.pexelsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pexelsapp.presentation.detail.DetailScreen
import com.example.pexelsapp.presentation.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Detail.route + "?photoId={photoId}",
            arguments = listOf(
                navArgument(
                    name = "photoId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            DetailScreen(navController)
        }
    }
}