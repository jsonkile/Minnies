package com.demo.minnies.orders.util

import com.demo.minnies.database.models.OrderWithItems
import com.demo.minnies.orders.presentation.models.OrderContent
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.getDateTime
import com.demo.minnies.shared.utils.toFormattedPriceWithSign

fun List<OrderWithItems>.toViewOrders(currency: Currency): List<ViewOrder> {
    return map { orderWithItems ->
        ViewOrder(
            id = orderWithItems.order.id,
            status = orderWithItems.order.status,
            progress = 90,
            createdTime = getDateTime(orderWithItems.order.createdTime),
            items = orderWithItems.items.map { item ->
                OrderContent(
                    amount = item.checkoutPrice.toFormattedPriceWithSign(currency),
                    quantity = item.quantity,
                    productId = item.productId,
                    productName = item.productName,
                    productImage = item.productImage
                )
            },
            ref = orderWithItems.order.ref,
            totalAmount = orderWithItems.items.sumOf { it.checkoutPrice }.toFormattedPriceWithSign(currency)
        )
    }
}