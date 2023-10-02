package com.example.pexelsapp.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pexelsapp.R
import com.example.pexelsapp.presentation.home.components.errorScreens.NoDataScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val isSaved = viewModel.isSaved.value

    val context = LocalContext.current

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                navController.popBackStack()
                            }
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = state.photo?.photographer ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier.padding(20.dp)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            if (state.error.isNotBlank()) {
                NoDataScreen(
                    modifier = Modifier.fillMaxSize(),
                    text = "Image not found",
                    textButton = "Explore",
                    navController = navController
                )
            } else {
                if (!state.isLoading) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.photo != null) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = state.photo.src.large,
                                ),
                                contentDescription = "image",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {}
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .width(180.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                state.photo?.let {
                                    viewModel.downloadImage(it.src.original)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "download",
                                tint = Color.White,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Download",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 10.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                state.photo?.let {
                                    if (!isSaved) viewModel.addBookmark(it)
                                    else viewModel.deleteBookmark(it)
                                }
                            }
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .align(Alignment.CenterEnd)
                    ) {
                        val painter =
                            if (isSaved) painterResource(id = R.drawable.bookmarks_button_active)
                            else painterResource(id = R.drawable.bookmarks_inactive)

                        val tint =
                            if (isSaved) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.onSurface

                        Icon(
                            painter = painter,
                            contentDescription = "add bookmark",
                            modifier = Modifier.padding(14.dp),
                            tint = tint
                        )
                    }
                }
            }
        }
    }
}