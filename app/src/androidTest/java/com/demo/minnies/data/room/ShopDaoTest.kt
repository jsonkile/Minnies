package com.demo.minnies.data.room

import com.demo.minnies.shop.data.daos.ShopDao
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.ShopItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ShopDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dao: ShopDao

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
                1L,
                dao.insert(
                    ShopItem(
                        name = "Test Item", category = Category.Shorts, image = "",
                        sizes = listOf(0), description = "", price = 4.0
                    )
                )
            )

            Assert.assertEquals(
                2L,
                dao.insert(
                    ShopItem(
                        name = "Test Item", category = Category.Shorts, image = "",
                        sizes = listOf(0), description = "", price = 4.0
                    )
                )
            )
        }
    }

    @Test
    fun getAll_ReturnsItems_WhenInserted() {
        runTest {
            dao.insert(
                ShopItem(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                ShopItem(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            Assert.assertEquals(2, dao.getAll().first().size)
        }
    }

    @Test
    fun getByCategory_ReturnsCorrectItems_WhenCategoryIsSpecified() {
        runTest {
            dao.insert(
                ShopItem(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                ShopItem(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                ShopItem(
                    name = "Test Item", category = Category.Top, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            Assert.assertEquals(1, dao.getItemsByCategory(Category.Top).first().size)
        }
    }
}