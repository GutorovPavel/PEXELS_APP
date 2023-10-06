package com.example.pexelsapp.presentation.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pexelsapp.R
import com.example.pexelsapp.data.remote.dto.PhotoDto
import com.example.pexelsapp.presentation.home.components.LoadingBar
import com.example.pexelsapp.presentation.home.components.errorScreens.ErrorScreen
import com.example.pexelsapp.presentation.home.components.PhotoItem
import com.example.pexelsapp.presentation.home.components.SearchBar
import com.example.pexelsapp.presentation.home.components.errorScreens.NoDataScreen
import com.example.pexelsapp.presentation.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val photoState = viewModel.photoState.value
    val photos = photoState.searchResult?.photos?: emptyList()

    val featuredState = viewModel.featuredState.value
    val chips = featuredState.featured?.collections

    val searchText by viewModel.searchText.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val gridState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold { paddingValue ->
        LaunchedEffect(Unit) {
            viewModel.isInternetAvailable(context)
            if (!viewModel.connection.value)
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            SearchBar(
                focusRequester = focusRequester,
                focusManager = focusManager,
                value = searchText,
                onValueChanged = viewModel::onSearchTextChangeWithUpdate,
                onEnter = { viewModel.onSearchTextChangeWithUpdate(searchText, 0L) },
                onDelete = {
                    viewModel.onSearchTextChangeWithUpdate("", 0L)
                    coroutineScope.launch {
                        gridState.scrollToItem(0)
                    }
                }
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
                            itemsIndexed(chips) { _, chip ->
                                InputChip(
                                    modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                                    selected = (chip.title.lowercase() == searchText.lowercase()),
                                    onClick = {
                                        viewModel.onSearchTextChangeWithUpdate(
                                            chip.title, 0L
                                        )
                                        coroutineScope.launch {
                                            gridState.scrollToItem(0)
                                        }
                                        focusManager.clearFocus()
                                    },
                                    label = {
                                        Text(
                                            text = chip.title,
                                            style = MaterialTheme.typography.labelSmall,
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
                    LoadingBar(PaddingValues(0.dp))
                } else {
                    if (photos != emptyList<PhotoDto>()) {
                        Spacer(modifier = Modifier.height(20.dp))

                        Box(Modifier.fillMaxSize()) {

                            LazyVerticalStaggeredGrid(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp),
                                columns = StaggeredGridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalItemSpacing = 16.dp,
                                state = gridState
                            ) {
                                items(photos) { photo ->
                                    PhotoItem(
                                        item = photo,
                                        onClick = {
                                            if (currentDestination?.route
                                                    ?.contains(Screen.Detail.route) == false
                                            ) {
                                                navController.navigate(
                                                    Screen.Detail.route
                                                            + "?photoId=${photo.id}"
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                            if (photoState.searchResult?.next_page != null) {
                                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                                    AnimatedVisibility(
                                        visible = gridState.isScrolled,
                                        enter = fadeIn(),
                                        exit = fadeOut()
                                    ) {
                                        Button(
                                            onClick = {
                                                viewModel.getPhotos(
                                                    searchText,
                                                    photoState.searchResult.page?.plus(1)
                                                )
                                                coroutineScope.launch {
                                                    gridState.scrollToItem(0)
                                                }
                                            },
                                            modifier = Modifier.padding(20.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.surface,
                                                contentColor = MaterialTheme.colorScheme.onSurface
                                            )
                                        ) {
                                            Text(text = stringResource(R.string.next_page))
                                        }
                                    }
                                }
                            }
                            if (photoState.searchResult?.page!! > 1) {
                                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                                    AnimatedVisibility(
                                        visible = gridState.onTop,
                                        enter = fadeIn(),
                                        exit = fadeOut()
                                    ) {
                                        Button(
                                            onClick = {
                                                viewModel.getPhotos(
                                                    searchText,
                                                    photoState.searchResult.page.minus(1)
                                                )
                                                coroutineScope.launch {
                                                    gridState.scrollToItem(0)
                                                }
                                            },
                                            modifier = Modifier.padding(20.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.surface,
                                                contentColor = MaterialTheme.colorScheme.onSurface
                                            )
                                        ) {
                                            Text(text = stringResource(R.string.go_to_previous_page))
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        if (viewModel.connection.value) {
                            NoDataScreen(
                                modifier = Modifier.fillMaxSize(),
                                text = stringResource(R.string.no_results_found),
                                textButton = stringResource(R.string.explore),
                                navController = navController,
                                onClick = {
                                    viewModel.onSearchTextChangeWithUpdate("", 0L)
                                }
                            )
                        } else {
                            ErrorScreen {
                                viewModel.getPhotos(searchText)
                            }
                        }
                    }
                }
                if (photoState.error.isNotBlank() && featuredState.error.isBlank()) {
                    ErrorScreen {
                        viewModel.getPhotos(searchText)
                    }
                }
                if (featuredState.error.isNotBlank()) {
                    ErrorScreen {
                        viewModel.getPhotos(searchText)
                        viewModel.getFeatured()
                    }
                }
            }
        }
    }
}

val LazyStaggeredGridState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 20

val LazyStaggeredGridState.onTop: Boolean
    get() = firstVisibleItemIndex < 8