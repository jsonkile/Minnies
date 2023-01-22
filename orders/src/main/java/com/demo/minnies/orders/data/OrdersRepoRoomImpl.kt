package com.demo.minnies.orders.data

import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.room.daos.OrdersDao
import javax.inject.Inject

class OrdersRepoRoomImpl @Inject constructor(private val ordersDao: OrdersDao) : OrdersRepo {
    override suspend fun addOrder(order: Order) = ordersDao.insert(order)

    override suspend fun updateOrder(orderIdAndStatus: OrderIdAndStatus) {
        ordersDao.update(orderIdAndStatus)
    }

    override fun getUserOrders(user: Long) = ordersDao.getAll(user)

    override fun getOrder(id: Long) = ordersDao.get(id)
}