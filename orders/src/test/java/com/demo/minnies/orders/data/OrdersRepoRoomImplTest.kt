package com.demo.minnies.orders.data

import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.database.room.daos.OrdersDao
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class OrdersRepoRoomImplTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var ordersDao: OrdersDao

    lateinit var ordersRepoRoomImpl: OrdersRepoRoomImpl

    @Before
    fun setup() {
        ordersRepoRoomImpl = OrdersRepoRoomImpl(ordersDao)
    }

    @Test
    fun testAdd() = runTest {
        val order = Order(user = 1, cartItems = listOf())
        coEvery { ordersDao.insert(order) } returns 1
        Assert.assertTrue(ordersRepoRoomImpl.addOrder(order) == 1L)
    }


    @Test
    fun testUpdate() = runTest {
        var order = Order(user = 1, cartItems = listOf(), status = OrderStatus.Completed)

        Assert.assertTrue(order.status == OrderStatus.Completed)

        coEvery {
            ordersDao.update(
                OrderIdAndStatus(1, status = OrderStatus.Ongoing)
            )
        } answers { order = order.copy(status = OrderStatus.Ongoing) }

        ordersRepoRoomImpl.updateOrder(OrderIdAndStatus(1, OrderStatus.Ongoing))

        Assert.assertTrue(order.status == OrderStatus.Ongoing)
    }

    @Test
    fun testGetUserOrders() = runTest {
        coEvery {
            ordersDao.getAll(1)
        } returns flow {
            emit(
                listOf(
                    Order(cartItems = listOf(), user = 1),
                    Order(cartItems = listOf(), user = 1)
                )
            )
        }

        Assert.assertEquals(2, ordersRepoRoomImpl.getUserOrders(1).first().size)
    }

    @Test
    fun testGetOrder() = runTest {
        coEvery {
            ordersDao.get(4)
        } returns flow {
            emit(Order(id = 4, cartItems = listOf(), user = 1, status = OrderStatus.Cancelled))
        }

        Assert.assertTrue(ordersRepoRoomImpl.getOrder(4).first()!!.status == OrderStatus.Cancelled)
    }
}