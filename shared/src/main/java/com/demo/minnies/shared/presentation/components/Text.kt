package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun TextWithIcon(
    modifier: Modifier,
    textAndIconGap: Dp,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        icon()
        Spacer(modifier = Modifier.width(textAndIconGap))
        text()
    }
}