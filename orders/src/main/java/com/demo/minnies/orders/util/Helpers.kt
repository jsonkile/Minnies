package com.demo.minnies.orders.util

import com.demo.minnies.database.models.OrderWithItems
import com.demo.minnies.orders.presentation.models.OrderContent
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.getDateTime
import com.demo.minnies.shared.utils.toFormattedPriceWithSign

fun OrderWithItems.toViewOrder(currency: Currency): ViewOrder {
    return ViewOrder(
        id = order.id,
        status = order.status,
        progress = 90,
        createdTime = getDateTime(order.createdTime),
        items = items.map { item ->
            OrderContent(
                amount = item.checkoutPrice.toFormattedPriceWithSign(currency),
                quantity = item.quantity,
                productId = item.productId,
                productName = item.productName,
                productImage = item.productImage
            )
        },
        ref = order.ref,
        totalAmount = items.sumOf { it.checkoutPrice }
            .toFormattedPriceWithSign(currency)
    )
}