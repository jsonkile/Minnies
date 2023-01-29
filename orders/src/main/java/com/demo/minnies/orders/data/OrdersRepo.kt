package com.demo.minnies.orders.data

import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.models.OrderItem
import com.demo.minnies.database.models.OrderWithItems
import kotlinx.coroutines.flow.Flow

interface OrdersRepo {
    suspend fun addOrder(order: Order, orderItems: List<OrderItem>)

    suspend fun updateOrder(orderIdAndStatus: OrderIdAndStatus)

    fun getUserOrders(user: Long): Flow<List<OrderWithItems>>

    fun getOrder(ref: String): Flow<OrderWithItems?>
}