package com.demo.minnies.shared.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MinniesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = appColors,
        content = content
    )
}