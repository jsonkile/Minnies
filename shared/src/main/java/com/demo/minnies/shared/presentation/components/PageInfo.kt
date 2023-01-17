package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.SearchOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageInfo(icon: ImageVector, message: String, modifier: Modifier, contentDescription: String) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8F)
            )
        )

        Text(
            text = message,
            modifier = Modifier.padding(top = 10.dp),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8F),
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp
        )

    }
}


@Preview
@Composable
fun PreviewPageInfo() {
    PageInfo(
        icon = Icons.TwoTone.SearchOff,
        message = "Your search failed, sorry!",
        contentDescription = "failed search",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}