package com.demo.minnies.orders.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun MyOrdersScreen(title: String) {

    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
        item {
            Text(
                modifier = Modifier.padding(
                    top = 25.dp,
                    bottom = 5.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
                text = title,
                style = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.Bold)
            )
        }

        item {
            Text(
                modifier = Modifier.padding(
                    bottom = 10.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
                text = "Secured and fulfilled by FedEx",
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)
            )
        }
    }

}


@Preview
@Composable
fun PreviewMyOrdersScreen() {
    MyOrdersScreen("Orders")
}