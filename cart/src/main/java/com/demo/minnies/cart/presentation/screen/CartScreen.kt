package com.demo.minnies.cart.presentation.screen

import androidx.compose.foundation.layout.*
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
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN

@Composable
fun CartScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(PAGE_HORIZONTAL_MARGIN),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            PageHeader(
                heading = "Shopping Cart",
                showSubHeading = false,
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
fun PreviewCartScreen() {
    CartScreen()
}