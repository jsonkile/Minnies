package com.demo.minnies.orders.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.orders.presentation.models.OrderContent
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.shared.presentation.ui.MinniesTheme

@Composable
fun OrderItemComposable(order: ViewOrder, modifier: Modifier) {
    ConstraintLayout(modifier) {
        val (content, title, date, amount, status) = createRefs()

        RoundedOrderContentsContainer(
            contents = order.items,
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(title.bottom, 5.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                    start.linkTo(parent.start)
                })

        Text(
            text = "Order Nº${order.ref}",
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(date.start, 10.dp)

                width = Dimension.fillToConstraints
            },
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
            )
        )

        Text(
            text = order.createdTime, modifier = Modifier.constrainAs(date) {
                top.linkTo(status.bottom)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)

            },
            fontSize = 10.sp,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground.copy(alpha = .7f)
        )

        Text(
            text = order.totalAmount,
            modifier = Modifier.constrainAs(amount) {
                top.linkTo(parent.top)
                bottom.linkTo(status.top)
                end.linkTo(parent.end)
            },
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = order.status.name,
            modifier = Modifier.constrainAs(status) {
                top.linkTo(amount.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(date.top)
            },
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
            fontSize = 12.sp
        )
    }
}


@Composable
@Preview
fun PreviewOrderItemComposable() {
    MinniesTheme {
        OrderItemComposable(
            ViewOrder(
                createdTime = "23/23/23",
                id = 1,
                progress = 40,
                status = OrderStatus.Ongoing,
                items = listOf(
                    OrderContent(
                        amount = "",
                        productImage = "",
                        productName = "",
                        productId = 1,
                        quantity = 1
                    ),
                    OrderContent(
                        amount = "",
                        productImage = "",
                        productName = "",
                        productId = 1,
                        quantity = 1
                    ),
                    OrderContent(
                        amount = "",
                        productImage = "",
                        productName = "",
                        productId = 1,
                        quantity = 1
                    )
                ),
                ref = "456YTRF",
                totalAmount = "$5.00"
            ),
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        )
    }
}