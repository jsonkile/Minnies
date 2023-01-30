package com.demo.minnies.shared.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteSwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }

    val scale = when (dismissState.progress.fraction) {
        in 0.0..0.2 -> 0f
        else -> dismissState.progress.fraction + 0.2f
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error, RoundedCornerShape(10.dp))
            .padding(horizontal = 30.dp),
        contentAlignment = alignment
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Delete cart item",
            modifier = Modifier
                .size(50.dp)
                .scale(scale),
            tint = Color.White
        )
    }
}