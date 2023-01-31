package com.demo.minnies.orders.presentation.screens.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.shared.presentation.components.TextWithIcon
import com.demo.minnies.shared.presentation.ui.MinniesTheme

@Composable
fun OrderSummary(viewOrder: ViewOrder, modifier: Modifier) {
    Column(modifier = modifier) {
        TextWithIcon(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 0.dp), textAndIconGap = 5.dp, icon = {
                Image(
                    imageVector = Icons.Default.TextSnippet,
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp),
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F)
                    )
                )
            },
            text = {
                androidx.compose.material.Text(
                    text = "Status",
                    modifier = Modifier.wrapContentSize(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                        fontSize = 13.sp, fontWeight = FontWeight.Normal,
                    )
                )
            })

        androidx.compose.material.Text(
            text = viewOrder.status.name,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 3.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        TextWithIcon(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp), textAndIconGap = 5.dp, icon = {
                Image(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp),
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F))
                )
            },
            text = {
                androidx.compose.material.Text(
                    text = "Date ordered",
                    modifier = Modifier.wrapContentSize(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                        fontSize = 13.sp, fontWeight = FontWeight.Normal,
                    )
                )
            })

        androidx.compose.material.Text(
            text = viewOrder.createdTime,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 3.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        TextWithIcon(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp), textAndIconGap = 5.dp, icon = {
                Image(
                    imageVector = Icons.Default.Money,
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp),
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F)
                    )
                )
            },
            text = {
                androidx.compose.material.Text(
                    text = "Total amount",
                    modifier = Modifier.wrapContentSize(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                        fontSize = 13.sp, fontWeight = FontWeight.Normal,
                    )
                )
            })

        androidx.compose.material.Text(
            text = viewOrder.totalAmount,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 3.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun PreviewOrderSummary() {
    MinniesTheme {
        OrderSummary(
            viewOrder = ViewOrder(
                status = OrderStatus.Ongoing,
                ref = "",
                id = 1L,
                progress = 90,
                items = listOf(),
                totalAmount = "N9,000",
                createdTime = "2 weeks ago"
            ), modifier = Modifier.fillMaxSize()
        )
    }
}