package com.example.pexelsapp.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.R
import com.example.pexelsapp.ui.theme.fontFamily

@Composable
fun SearchBar(
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    value: String,
    onValueChanged:(String) -> Unit,
    onEnter:(String) -> Unit,
    onDelete:() -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 24.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(30.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "",
                modifier = Modifier.weight(1f)
            )
            Row(
                modifier = Modifier.weight(7f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier.weight(6f),
                    value = value,
                    onValueChange = onValueChanged,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onEnter(value)
                            focusManager.clearFocus()
                        }
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = fontFamily
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search),
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                AnimatedVisibility(
                    modifier = Modifier.weight(1f),
                    visible = value.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = {
                            onDelete()
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}