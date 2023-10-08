package com.example.pexelsapp.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBarComponent(
    navController: NavController
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Bookmarks
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarScreen = items.any { it.route == currentDestination?.route }

    AnimatedVisibility(
        visible = bottomBarScreen,
        enter = fadeIn(
            animationSpec = tween(400)
        ),
        exit = fadeOut(
            animationSpec = tween(0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(BottomAppBarDefaults.ContainerElevation)
                .zIndex(1f)
                .navigationBarsPadding()
                .height(70.dp)
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedItemIndex == index
                val visuallySelected = currentDestination?.route == item.route
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(0.5f)
                ) {
                    AnimatedVisibility(
                        visible = visuallySelected,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Divider(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 3.dp,
                            modifier = Modifier
                                .width(20.dp)
                        )
                    }
                    this@Row.NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (item.route != currentDestination?.route) {
                                selectedItemIndex = index
                                navController.navigate(item.route) {
                                    if (currentDestination != null) {
                                        popUpTo(id = currentDestination.id) {
                                            saveState = true
                                            inclusive = true
                                        }
                                    } else {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                            inclusive = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            val painter = if (isSystemInDarkTheme()) {
                                // check icon not by index cause of ability to navigate not from navigation bar
                                if (visuallySelected) painterResource(
                                    item.selectedIconNight
                                )
                                else painterResource(item.unselectedIcon)
                            } else {
                                if (visuallySelected) painterResource(
                                    item.selectedIcon
                                )
                                else painterResource(item.unselectedIcon)
                            }

                            Icon(
                                painter = painter,
                                contentDescription = item.title,
                                tint = Color.Unspecified
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    }
}
