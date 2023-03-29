package com.demo.minnies.database.data.room

import com.demo.minnies.database.models.*
import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.daos.OrdersDao
import com.demo.minnies.database.room.daos.ProductsDao
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

    @Inject
    lateinit var cartDao: CartDao

    @Inject
    lateinit var productsDao: ProductsDao

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
        dao.insert(Order(ref = "AA", user = 1))
        dao.insert(Order(ref = "AB", user = 1))
        Assert.assertTrue(dao.getAll(1).first().size == 2)
    }

    @Test
    fun testGet() = runTest {
        dao.insert(Order(ref = "AA", user = 1, status = OrderStatus.Cancelled))
        dao.insert(Order(ref = "AB", user = 1))
        Assert.assertTrue(dao.get("AA").first()!!.order.status == OrderStatus.Cancelled)
    }

    @Test
    fun testUpdate() = runTest {
        dao.insert(Order(ref = "AB", user = 1, status = OrderStatus.Cancelled))
        dao.update(OrderIdAndStatus(ref = "AB", OrderStatus.Ongoing))
        Assert.assertTrue(dao.get("AB").first()!!.order.status == OrderStatus.Ongoing)
    }

    @Test
    fun testInsertOrderAndItems() = runTest {
        dao.insertOrderAndItems(
            Order(ref = "AA", user = 1),
            items = listOf(OrderItem(orderRef = "AA", productId = 1, quantity = 9))
        )

        productsDao.insert(
            Product(
                name = "omega",
                productCategory = ProductCategory.Accessory,
                image = "",
                description = "",
                price = 0.2
            )
        )

        Assert.assertTrue(dao.get("AA").first()!!.order.status == OrderStatus.Ongoing)
        Assert.assertTrue(dao.get("AA").first()!!.items.size == 1)
        Assert.assertTrue(dao.get("AA").first()!!.items.first().productId == 1L)
        Assert.assertTrue(dao.get("AA").first()!!.items.first().quantity == 9)
        Assert.assertTrue(dao.get("AA").first()!!.items.first().productName == "omega")
    }
}