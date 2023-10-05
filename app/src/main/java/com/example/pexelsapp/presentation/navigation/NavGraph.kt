package com.example.pexelsapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pexelsapp.presentation.bookmarks.BookmarksScreen
import com.example.pexelsapp.presentation.detail.DetailScreen
import com.example.pexelsapp.presentation.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(
                        200,
                        easing = EaseOutSine
                    ),
                    initialOffsetX = { fullWidth ->
                        -fullWidth
                    }
                )
            },
            exitTransition = {
                if (targetState.destination.route?.contains(Screen.Detail.route) == true) {
                    ExitTransition.None
                } else {
                    slideOutHorizontally(
                        animationSpec = tween(
                            200,
                            easing = EaseOutSine
                        ),
                        targetOffsetX = { fullWidth ->
                            -fullWidth
                        }
                    )
                }
            },
            popEnterTransition = {
                EnterTransition.None
            }

        ) {
            HomeScreen(navController, paddingValues = paddingValues)
        }
        composable(
            route = Screen.Bookmarks.route,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(
                        200,
                        easing = EaseOutSine
                    ),
                    initialOffsetX = { fullWidth ->
                        fullWidth
                    }
                )
            },
            exitTransition = {
                if (targetState.destination.route?.contains(Screen.Detail.route) == true) {
                    ExitTransition.None
                } else {
                    slideOutHorizontally(
                        animationSpec = tween(
                            200,
                            easing = EaseOutSine
                        ),
                        targetOffsetX = { fullWidth ->
                            -fullWidth
                        }
                    )
                }
            },
            popEnterTransition = {
                EnterTransition.None
            }
        ) {
            BookmarksScreen(navController, paddingValues = paddingValues)
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
            ),
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        300,
                        easing = EaseOutCubic
                    ),
                    initialOffsetY = { fullHeight ->
                        fullHeight
                    }
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        300,
                        easing = EaseOutCubic
                    ),
                    targetOffsetY = { fullHeight ->
                        fullHeight
                    }
                )
            },
            popExitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        300,
                        easing = EaseOutCubic
                    ),
                    targetOffsetY = { fullHeight ->
                        fullHeight
                    }
                )
            }
        ) {
            DetailScreen(navController)
        }
    }
}