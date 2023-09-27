package com.example.pexelsapp.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pexelsapp.data.remote.dto.PhotoDto
import com.example.pexelsapp.util.pxToDp

@Composable
fun PhotoItem(
    item: PhotoDto,
    onClick: (PhotoDto) -> Unit
) {
//    For coil we need to specify the height.
//    let's calculate it by dividing the original height
//    by the compression of the picture width (screen width / 2 - padding).

    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val multiplier = item.width.pxToDp(density) / (screenWidth/2 - 30.dp)
    val height = item.height.pxToDp(density) / multiplier

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(item) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = item.src.large2x,
                contentScale = ContentScale.FillHeight
            ),
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
    }
}