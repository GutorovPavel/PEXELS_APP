package com.example.pexelsapp.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    error: String
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = error,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}