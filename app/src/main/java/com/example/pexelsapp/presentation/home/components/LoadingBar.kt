package com.example.pexelsapp.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun LoadingBar(
    paddingValues: PaddingValues
) {
    val showLoading = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(400)
        showLoading.value = true
    }
    AnimatedVisibility(
        visible = showLoading.value,
        enter = fadeIn()
    ) {
        LinearProgressIndicator(
            color = MaterialTheme.colorScheme.primaryContainer,
            trackColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        )
    }
}