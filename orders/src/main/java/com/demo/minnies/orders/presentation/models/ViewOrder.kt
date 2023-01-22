package com.demo.minnies.orders.presentation.models

import com.demo.minnies.database.models.OrderStatus

data class ViewOrder(
    val id: Long,
    val status: OrderStatus,
    val createdTime: String,
    val items: List<OrderContent>,
    val progress: Int
)

data class OrderContent(
    val productId: Long,
    val productImage: String,
    val productName: String,
    val quantity: Int,
    val amount: String
)
