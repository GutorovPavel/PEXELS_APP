package com.example.pexelsapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val photos = viewModel.state.value.searchResult?.photos
    val isLoading = viewModel.state.value.isLoading

    Column(Modifier.fillMaxSize()){
        Text(text = photos.toString())
    }
}