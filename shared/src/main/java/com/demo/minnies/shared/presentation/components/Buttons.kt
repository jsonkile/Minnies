package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.minnies.shared.presentation.ui.MinniesTheme

@Composable
fun MinniesDefaultButton(
    modifier: Modifier,
    text: String,
    icon: ImageVector? = null,
    iconContentDescription: String = "",
    isLoading: Boolean = false,
    enabled: Boolean = true,
    clickAction: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = { if (isLoading.not()) clickAction() }) {

        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            icon?.let {
                Image(
                    imageVector = icon,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                    contentDescription = iconContentDescription,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(16.dp)
                )
            }

            Text(
                text = text,
                modifier = Modifier,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            )

            if (isLoading) {

                Spacer(modifier = Modifier.width(10.dp))

                CircularProgressIndicator(
                    modifier = Modifier.size(13.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewDefaultButton() {
    MinniesTheme {
        MinniesDefaultButton(
            modifier = Modifier.wrapContentSize(),
            text = "Add to bag",
            icon = Icons.Outlined.AddShoppingCart,
            isLoading = false
        ) {

        }
    }
}


@Composable
fun MinniesOutlinedDefaultButton(
    modifier: Modifier,
    text: String,
    icon: ImageVector? = null,
    iconContentDescription: String = "",
    isLoading: Boolean = false,
    enabled: Boolean = true,
    clickAction: () -> Unit
) {

    OutlinedButton(
        modifier = modifier,
        enabled = enabled,
        onClick = { if (isLoading.not()) clickAction() }) {

        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            icon?.let {
                Image(
                    imageVector = icon,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                    contentDescription = iconContentDescription,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(21.dp)
                )
            }

            Text(
                text = text,
                modifier = Modifier,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            )



            if (isLoading) {

                Spacer(modifier = Modifier.width(10.dp))

                CircularProgressIndicator(
                    modifier = Modifier.size(10.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )

            }
        }

    }
}


@Preview
@Composable
fun PreviewOutlinedButton() {
    MinniesTheme {
        Column() {
            MinniesOutlinedDefaultButton(
                modifier = Modifier
                    .wrapContentSize(),
                text = "Add to Bag",
                icon = Icons.Outlined.AddShoppingCart,
                isLoading = true
            ) {

            }

            MinniesOutlinedDefaultButton(
                modifier = Modifier
                    .wrapContentSize(),
                text = "Add to Bag",
                isLoading = true
            ) {

            }
        }
    }
}