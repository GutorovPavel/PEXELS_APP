package com.example.pexelsapp.presentation.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pexelsapp.presentation.bookmarks.components.BookmarkItem
import com.example.pexelsapp.presentation.home.components.errorScreens.NoDataScreen
import com.example.pexelsapp.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    navController: NavController,
    viewModel: BookmarksViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val photos = viewModel.photos.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Bookmarks",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                },
                modifier = Modifier.padding(20.dp)
            )
        }
    ) { paddingValue ->

        if (photos.value.isEmpty()) {
            NoDataScreen(
                modifier = Modifier.fillMaxSize().padding(paddingValue),
                text = "You haven't saved anything yet",
                textButton = "Explore",
                navController = navController
            )
        } else {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValue)
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .padding(horizontal = 24.dp),
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp
            ) {
                items(photos.value) { photo ->
                    BookmarkItem(
                        item = photo,
                        onClick = {
                            if (currentDestination?.route
                                    ?.contains(Screen.Detail.route) == false) {
                                navController.navigate(Screen.Detail.route
                                        + "?photoId=${photo.id}")
                            }
                        }
                    )
                }
            }
        }
    }
}