package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN

@Composable
fun MinniesToolbar(
    toolBarTitle: String,
    showNavigationIcon: Boolean = false,
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
                .padding(horizontal = PAGE_HORIZONTAL_MARGIN - 3.dp),
            horizontalArrangement = Arrangement.Start
        ) {

            if (showNavigationIcon) {
                Image(
                    imageVector = navigationButtonIcon,
                    contentDescription = "back button",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .clickable(
                            indication = rememberRipple(
                                bounded = true
                            ),
                            onClick = navigationButtonAction,
                            interactionSource = MutableInteractionSource()
                        )
                        .padding(6.dp)
                )
            }

            Text(
                text = toolBarTitle,
                modifier = Modifier.padding(start = if (showNavigationIcon) 15.dp else 0.dp),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 20.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewMinniesToolbar() {
    MinniesTheme {
        Column {
            MinniesToolbar(
                toolBarTitle = "Favourites",
                navigationButtonIcon = Icons.Default.ArrowBack,
                navigationButtonAction = {})

            MinniesToolbar(
                toolBarTitle = "Favourites",
                navigationButtonIcon = Icons.Default.ArrowBack,
                navigationButtonAction = {},
                showNavigationIcon = true
            )
        }
    }
}