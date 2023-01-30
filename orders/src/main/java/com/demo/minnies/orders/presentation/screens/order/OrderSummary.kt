package com.demo.minnies.orders.presentation.screens.order

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.shared.presentation.ui.MinniesTheme

@Composable
fun OrderSummary(viewOrder: ViewOrder, modifier: Modifier) {
    Column(modifier = modifier) {

        Text(
            text = "Order Nº${viewOrder.ref}",
            modifier = Modifier.padding(top = 0.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
                fontSize = 25.sp
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Status",
                modifier = Modifier.weight(3F),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(7F)) {
                Text(
                    text = viewOrder.status.name,
                    textAlign = TextAlign.End,
                    color = when (viewOrder.status) {
                        OrderStatus.Ongoing -> MaterialTheme.colorScheme.onTertiaryContainer
                        OrderStatus.Completed -> MaterialTheme.colorScheme.primary
                        OrderStatus.Cancelled -> MaterialTheme.colorScheme.onError
                    },
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = when (viewOrder.status) {
                        OrderStatus.Ongoing -> "2 days to delivery"
                        OrderStatus.Completed -> "Sealed and delivered"
                        OrderStatus.Cancelled -> "Something went wrong? You can report an issue"
                    },
                    textAlign = TextAlign.End,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Date ordered",
                modifier = Modifier.weight(5F),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(5F)) {
                Text(
                    text = viewOrder.createdTime,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total payment",
                modifier = Modifier.weight(4F),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(6F)) {
                Text(
                    text = viewOrder.totalAmount,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = "+ Free delivery",
                    textAlign = TextAlign.End,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
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