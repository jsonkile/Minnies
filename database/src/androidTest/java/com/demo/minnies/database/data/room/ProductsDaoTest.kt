package com.demo.minnies.database.data.room

import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.daos.ProductsDao
import com.demo.minnies.database.room.models.Category
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
class ProductsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dao: ProductsDao

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
                    Product(
                        name = "Test Item", category = Category.Shorts, image = "",
                        sizes = listOf(0), description = "", price = 4.0
                    )
                )
            )

            Assert.assertEquals(
                2L,
                dao.insert(
                    Product(
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
                Product(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                Product(
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
                Product(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                Product(
                    name = "Test Item", category = Category.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                Product(
                    name = "Test Item", category = Category.Top, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            Assert.assertEquals(1, dao.getItemsByCategory(Category.Top).first().size)
        }
    }

    @Test
    fun getProduct_ReturnsCorrectProduct() = runTest {
        dao.insert(
            Product(
                name = "Test Item 1", category = Category.Shorts, image = "",
                sizes = listOf(0), description = "", price = 4.0
            )
        )

        dao.insert(
            Product(
                name = "Test Item 2", category = Category.Shorts, image = "",
                sizes = listOf(0), description = "", price = 4.0
            )
        )

        Assert.assertEquals("Test Item 2", dao.getProduct(2).first()?.name)
        Assert.assertEquals("Test Item 1", dao.getProduct(1).first()?.name)
    }
}