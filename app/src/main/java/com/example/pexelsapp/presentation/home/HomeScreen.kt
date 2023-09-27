package com.example.pexelsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pexelsapp.presentation.home.components.ErrorScreen
import com.example.pexelsapp.presentation.home.components.NavigationBarComponent
import com.example.pexelsapp.presentation.home.components.PhotoItem
import com.example.pexelsapp.presentation.home.components.SearchBar
import com.example.pexelsapp.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val photoState = viewModel.photoState.value
    val photos = photoState.searchResult?.photos

    val featuredState = viewModel.featuredState.value
    val chips = featuredState.featured?.collections

    val searchText by viewModel.searchText.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            SearchBar(
                focusRequester = focusRequester,
                focusManager = focusManager,
                value = searchText,
                onValueChanged = viewModel::onSearchTextChangeWithUpdate,
                onEnter = { viewModel.onSearchTextChangeWithUpdate(searchText, 0L) },
                onDelete = { viewModel.onSearchTextChangeWithUpdate("", 0L) }
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(!featuredState.isLoading) {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        item {
                            Spacer(modifier = Modifier.width(21.dp))
                        }
                        if (chips != null) {
                            items(chips) { chip ->
                                InputChip(
                                    modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                                    selected = (chip.title.lowercase() == searchText.lowercase()),
                                    onClick = {
                                        viewModel.onSearchTextChangeWithUpdate(
                                            chip.title, 0L
                                        )
                                        focusManager.clearFocus()
                                    },
                                    label = {
                                        Text(
                                            text = chip.title,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    },
                                    colors = InputChipDefaults.inputChipColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        labelColor = MaterialTheme.colorScheme.onSurface,
                                        selectedLabelColor = Color.White
                                    ),
                                    border = InputChipDefaults.inputChipBorder(
                                        borderColor = Color.Transparent
                                    ),
                                    shape = CircleShape
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.width(21.dp))
                        }
                    }
                }
                if(photoState.isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        trackColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        columns = StaggeredGridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalItemSpacing = 16.dp
                    ) {
                        if (photos != null) {
                            items(photos) { photo ->
                                PhotoItem(
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
                if (photoState.error.isNotBlank() || featuredState.error.isNotBlank()) {
                    ErrorScreen(error = photoState.error)
                }
            }
        }
    }
}