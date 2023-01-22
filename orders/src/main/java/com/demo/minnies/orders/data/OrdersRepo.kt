package com.demo.minnies.orders.data

import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderIdAndStatus
import kotlinx.coroutines.flow.Flow

interface OrdersRepo {
    suspend fun addOrder(order: Order): Long

    suspend fun updateOrder(orderIdAndStatus: OrderIdAndStatus)

    fun getUserOrders(user: Long): Flow<List<Order>>

    fun getOrder(id: Long): Flow<Order?>
}