package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.minnies.shared.presentation.ui.MinniesTheme

@Composable
fun PageHeader(heading: String, subHeading: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = heading,
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground
            )
        )

        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = subHeading,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6F)
            )
        )
    }
}

@Preview
@Composable
fun PreviewPageHeader() {
    MinniesTheme {
        PageHeader(
            heading = "Login",
            subHeading = "I de outside!",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}