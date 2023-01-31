package com.demo.minnies.orders.data

import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.models.OrderItem
import com.demo.minnies.database.room.daos.OrdersDao
import com.demo.minnies.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersRepoRoomImpl @Inject constructor(
    private val ordersDao: OrdersDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : OrdersRepo {
    override suspend fun addOrder(order: Order, orderItems: List<OrderItem>) =
        ordersDao.insertOrderAndItems(order, orderItems)

    override suspend fun updateOrder(orderIdAndStatus: OrderIdAndStatus) =
        withContext(ioDispatcher) {
            ordersDao.update(orderIdAndStatus)
        }

    override fun getUserOrders(user: Long) = ordersDao.getUserOrders(user)

    override fun getOrder(ref: String) = ordersDao.get(ref)
}