package com.example.pexelsapp.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
//            enterTransition = {
//                slideInHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    initialOffsetX = { fullWidth ->
//                        -fullWidth
//                    }
//                )
//            },
//            exitTransition = {
//                slideOutHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    targetOffsetX = { fullWidth ->
//                        -fullWidth
//                    }
//                )
//            },
//            popEnterTransition = {
//                slideInHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    initialOffsetX = { fullWidth ->
//                        -fullWidth
//                    }
//                )
//            },
//            popExitTransition = {
//                slideOutHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    targetOffsetX = { fullWidth ->
//                        -fullWidth
//                    }
//                )
//            }
        ) {
            HomeScreen(navController, paddingValues = paddingValues)
        }
        composable(
            route = Screen.Bookmarks.route,
//            enterTransition = {
//                if (route == Screen.Home.route) {
//
//                }
//                 slideInHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    initialOffsetX = { fullWidth ->
//                        fullWidth
//                    }
//                )
//            },
//            exitTransition = {
//                slideOutHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    targetOffsetX = { fullWidth ->
//                        fullWidth
//                    }
//                )
//            },
//            popEnterTransition = {
//                slideInHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    initialOffsetX = { fullWidth ->
//                        -fullWidth
//                    }
//                )
//            },
//            popExitTransition = {
//                slideOutHorizontally(
//                    animationSpec = tween(
//                        400,
//                        easing = EaseOutCubic
//                    ),
//                    targetOffsetX = { fullWidth ->
//                        -fullWidth
//                    }
//                )
//            }
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
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            DetailScreen(navController)
        }
    }
}