package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenInfoView(
    message: String, icon: ImageVector, modifier: Modifier, showAsError: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            imageVector = icon,
            contentDescription = "error icon",
            modifier = Modifier.size(70.dp),
            colorFilter = ColorFilter.tint(if (showAsError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground)
        )

        Text(
            text = message,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp),
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = if (showAsError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
            ),
        )

    }
}

@Preview
@Composable
fun PreviewScreenInfoView() {
    ScreenInfoView(
        message = "Something went wrong",
        icon = Icons.Default.HeartBroken,
        modifier = Modifier.wrapContentSize(),
        showAsError = false
    )
}

@Composable
fun ErrorBar(message: String, modifier: Modifier) {
    Box(modifier = modifier) {
        Text(
            text = message,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.error, shape = RoundedCornerShape(5.dp)
                )
                .padding(vertical = 6.dp, horizontal = 10.dp),
            color = MaterialTheme.colorScheme.errorContainer,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun PreviewErrorBar() {
    ErrorBar(
        message = "Something went wrong", modifier = Modifier.wrapContentSize()
    )
}