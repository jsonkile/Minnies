package com.demo.minnies.orders.data

import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.database.models.OrderWithItems
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
        var step = 0
        val order = Order(user = 1, ref = "")
        coEvery { ordersDao.insertOrderAndItems(order, listOf()) } answers { step++ }
        ordersRepoRoomImpl.addOrder(order = order, orderItems = emptyList())
        Assert.assertTrue(1 == step)
    }

    @Test
    fun testUpdate() = runTest {
        var order = Order(user = 1, status = OrderStatus.Completed, ref = "")

        Assert.assertTrue(order.status == OrderStatus.Completed)

        coEvery {
            ordersDao.update(OrderIdAndStatus(status = OrderStatus.Ongoing, ref = ""))
        } answers { order = order.copy(status = OrderStatus.Ongoing) }

        ordersRepoRoomImpl.updateOrder(OrderIdAndStatus(status = OrderStatus.Ongoing, ref = ""))

        Assert.assertTrue(order.status == OrderStatus.Ongoing)
    }

    @Test
    fun testGetUserOrders() = runTest {
        coEvery { ordersDao.getUserOrders(1) } returns flow {
            emit(
                listOf(
                    OrderWithItems(order = Order(ref = "", user = 1), items = emptyList()),
                    OrderWithItems(order = Order(ref = "", user = 1), items = emptyList())
                )
            )
        }

        Assert.assertEquals(2, ordersRepoRoomImpl.getUserOrders(1).first().size)
    }

    @Test
    fun testGetOrder() = runTest {
        coEvery { ordersDao.get("") } returns flow {
            emit(
                OrderWithItems(
                    Order(id = 4, user = 1, status = OrderStatus.Cancelled, ref = ""),
                    listOf()
                )
            )
        }

        Assert.assertTrue(
            ordersRepoRoomImpl.getOrder("").first()!!.order.status == OrderStatus.Cancelled
        )
    }
}