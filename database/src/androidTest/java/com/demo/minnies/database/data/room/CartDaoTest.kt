package com.demo.minnies.database.data.room

import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.daos.ProductsDao
import com.demo.minnies.database.room.models.CartItem
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.PartialCartItem
import com.demo.minnies.database.room.models.Product
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class CartDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dao: CartDao

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
    fun getAll_ReturnsZeroItems_WhenTableIsEmpty() {
        runTest {
            Assert.assertEquals(0, dao.getAll().first().size)
        }
    }

    @Test
    fun insert_AddsNewItem_WhenItemPassed() {
        runTest {
            Assert.assertEquals(
                1L, dao.insert(CartItem(productId = 9, quantity = 2, userId = 1))
            )

            Assert.assertEquals(
                2L, dao.insert(CartItem(productId = 8, quantity = 2, userId = 1))
            )
        }
    }

    @Test
    fun update_UpdatesItem_WhenItemPassed() {
        runTest {
            dao.insert(CartItem(productId = 9, quantity = 2, userId = 1, id = 1))
            dao.update(PartialCartItem(id = 1, 6))

            Assert.assertEquals(dao.getAll().first().first().quantity, 6)
        }
    }

    @Test
    fun delete_RemovesItem() {
        runTest {
            dao.insert(CartItem(productId = 9, quantity = 2, userId = 1))
            dao.insert(CartItem(productId = 6, quantity = 2, userId = 1))

            Assert.assertEquals(dao.getAll().first().size, 2)

            dao.delete(1)
            Assert.assertEquals(dao.getAll().first().size, 1)
        }
    }

    @Test
    fun getAll_ReturnsItems_WhenInserted() {
        runTest {
            dao.insert(CartItem(productId = 9, quantity = 2, userId = 1))
            dao.insert(CartItem(productId = 18, quantity = 2, userId = 1))
            dao.insert(CartItem(productId = 4, quantity = 2, userId = 1))

            Assert.assertEquals(3, dao.getAll().first().size)
        }
    }
}