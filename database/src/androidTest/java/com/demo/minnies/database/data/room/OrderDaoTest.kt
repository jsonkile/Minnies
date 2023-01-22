package com.demo.minnies.database.data.room

import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderIdAndStatus
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.daos.OrdersDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class OrderDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dao: OrdersDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun finish() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun testInsertAndGetAll() = runTest {
        Assert.assertTrue(dao.getAll(1).first().isEmpty())
        dao.insert(Order(cartItems = listOf(1, 2), user = 1))
        dao.insert(Order(cartItems = listOf(1, 2), user = 1))
        Assert.assertTrue(dao.getAll(1).first().size == 2)
    }

    @Test
    fun testGet() = runTest {
        dao.insert(Order(cartItems = listOf(1, 2), user = 1, status = OrderStatus.Cancelled))
        dao.insert(Order(cartItems = listOf(1, 2), user = 1))
        Assert.assertTrue(dao.get(1).first()!!.status == OrderStatus.Cancelled)
    }

    @Test
    fun testUpdate() = runTest {
        dao.insert(Order(cartItems = listOf(1, 2), user = 1, status = OrderStatus.Cancelled))
        dao.update(OrderIdAndStatus(1, OrderStatus.Ongoing))
        Assert.assertTrue(dao.get(1).first()!!.status == OrderStatus.Ongoing)
    }
}