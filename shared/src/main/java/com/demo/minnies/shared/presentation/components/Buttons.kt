package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.minnies.shared.presentation.ui.MinniesTheme

@Composable
fun DefaultButton(
    modifier: Modifier,
    text: String,
    icon: ImageVector? = null,
    clickAction: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { clickAction() }) {

        Row {

            icon?.let {
                Image(
                    imageVector = icon,
                    contentDescription = "add to bag",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.padding(end = 5.dp).size(21.dp)
                )
            }

            Text(
                text = text,
                modifier = Modifier,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewDefaultButton() {
    MinniesTheme {
        DefaultButton(
            modifier = Modifier
                .wrapContentSize(),
            text = "Add to Bag",
            icon = Icons.Outlined.AddShoppingCart
        ) {

        }
    }
}