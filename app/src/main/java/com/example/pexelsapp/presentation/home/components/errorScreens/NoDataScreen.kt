package com.example.pexelsapp.presentation.home.components.errorScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pexelsapp.presentation.navigation.Screen

@Composable
fun NoDataScreen(
    modifier: Modifier,
    text: String,
    textButton: String,
    navController: NavController,
    onClick:() -> Unit? = {}
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall
            )
            TextButton(onClick = {
                if (currentDestination?.route != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        if (currentDestination != null) {
                            popUpTo(currentDestination.id) {
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
                } else {
                    onClick()
                }
            }) {
                Text(
                    text = textButton,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}