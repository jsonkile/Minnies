package com.demo.minnies.orders.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.shared.presentation.components.PageHeader
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN

@Composable
fun MyOrdersScreen(title: String) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(PAGE_HORIZONTAL_MARGIN),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            PageHeader(
                heading = "Orders",
                subHeading = "Your orders are safe here.",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 20.dp, bottom = 30.dp)
            )
        }
    }

}


@Preview
@Composable
fun PreviewMyOrdersScreen() {
    MinniesTheme {
        MyOrdersScreen("Orders")
    }
}