package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN

@Composable
fun MinniesToolbar(
    toolBarTitle: String,
    navigationButtonIcon: ImageVector = Icons.Default.ArrowBack,
    navigationButtonAction: () -> Unit
) {
    Surface(
        shadowElevation = 0.dp,
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(65.dp)
                .fillMaxWidth()
                .padding(horizontal = PAGE_HORIZONTAL_MARGIN),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                imageVector = navigationButtonIcon,
                contentDescription = "back button",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .size(27.dp)
                    .clickable {
                        navigationButtonAction()
                    }
            )

            Text(
                text = toolBarTitle,
                modifier = Modifier.padding(start = 0.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 19.sp
                )
            )

            Row() {

            }
        }
    }
}

@Preview
@Composable
fun PreviewMinniesToolbar() {
    MinniesTheme {
        MinniesToolbar(
            toolBarTitle = "Favourites",
            navigationButtonIcon = Icons.Default.ArrowBack,
            navigationButtonAction = {})
    }
}