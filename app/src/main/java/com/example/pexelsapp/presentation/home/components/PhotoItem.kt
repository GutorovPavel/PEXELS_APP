package com.example.pexelsapp.presentation.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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

    val showShimmer = remember { mutableStateOf(true) }

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .bounceClick()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(item) }
    ) {
        AsyncImage(
            model = item.src.medium,
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
                .fillMaxSize(),
            onSuccess = { showShimmer.value = false }
        )
    }
}

enum class ButtonState { Pressed, Idle }
fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) 0.9f else 1f
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}