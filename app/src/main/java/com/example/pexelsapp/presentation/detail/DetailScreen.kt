package com.example.pexelsapp.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pexelsapp.R
import com.example.pexelsapp.presentation.home.components.LoadingBar
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

    val isImageLoading = remember { mutableStateOf(true) }
    var scale by remember { mutableFloatStateOf(1f) }


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
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
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

        if (state.error.isNotBlank()) {
            NoDataScreen(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(R.string.image_not_found),
                textButton = stringResource(R.string.explore),
                navController = navController
            )
        } else {
            if (isImageLoading.value) {
                LoadingBar(paddingValues = paddingValues)
            }
            if (!state.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                        .padding(horizontal = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .weight(1f)
                            .pointerInput(Unit) {
                                detectTransformGestures { _, _, zoom, _ ->
                                    scale *= zoom
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.photo != null) {
                            AsyncImage(
                                model = state.photo.src.large,
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                                    .graphicsLayer(
                                        scaleX = maxOf(1f, minOf(3f, scale)),
                                        scaleY = maxOf(1f, minOf(3f, scale)),
                                    )
                                    .clip(RoundedCornerShape(16.dp)),
                                onSuccess = { isImageLoading.value = false }
                            )
                        }
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
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .clickable {
                                    state.photo?.let {
                                        viewModel.downloadImage(it.src.original)
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.saved),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = stringResource(R.string.download),
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
                                        else viewModel.deleteBookmark(it.id)
                                    }
                                }
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .align(Alignment.CenterEnd)
                        ) {
                            val painter =
                                if (isSaved) painterResource(id = R.drawable.bookmarks_button_active)
                                else painterResource(id = R.drawable.bookmarks_inactive)

                            val tint =
                                if (isSaved) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface

                            Icon(
                                painter = painter,
                                contentDescription = "",
                                modifier = Modifier.padding(14.dp),
                                tint = tint
                            )
                        }
                    }
                }
            }
        }
    }
}