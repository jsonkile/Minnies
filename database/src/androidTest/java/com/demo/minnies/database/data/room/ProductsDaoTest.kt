package com.demo.minnies.database.data.room

import com.demo.minnies.database.models.Product
import com.demo.minnies.database.models.ProductCategory
import com.demo.minnies.database.room.AppDatabase
import com.demo.minnies.database.room.daos.ProductsDao
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
    fun testInsert() {
        runTest {
            Assert.assertEquals(
                1L,
                dao.insert(
                    Product(
                        name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                        sizes = listOf(0), description = "", price = 4.0
                    )
                )
            )

            Assert.assertEquals(
                2L,
                dao.insert(
                    Product(
                        name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                        sizes = listOf(0), description = "", price = 4.0
                    )
                )
            )
        }
    }

    @Test
    fun testGetAll() {
        runTest {
            dao.insert(
                Product(
                    name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                Product(
                    name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            Assert.assertEquals(2, dao.getAll().first().size)
        }
    }

    @Test
    fun testCountAll() {
        runTest {
            dao.insert(
                Product(
                    name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                Product(
                    name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            Assert.assertEquals(2, dao.countAll().first())
        }
    }

    @Test
    fun testGetByCategory() {
        runTest {
            dao.insert(
                Product(
                    name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                Product(
                    name = "Test Item", productCategory = ProductCategory.Shorts, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            dao.insert(
                Product(
                    name = "Test Item", productCategory = ProductCategory.Top, image = "",
                    sizes = listOf(0), description = "", price = 4.0
                )
            )

            Assert.assertEquals(1, dao.getItemsByCategory(ProductCategory.Top).first().size)
        }
    }

    @Test
    fun testGet() = runTest {
        dao.insert(
            Product(
                name = "Test Item 1", productCategory = ProductCategory.Shorts, image = "",
                sizes = listOf(0), description = "", price = 4.0
            )
        )

        dao.insert(
            Product(
                name = "Test Item 2", productCategory = ProductCategory.Shorts, image = "",
                sizes = listOf(0), description = "", price = 4.0
            )
        )

        Assert.assertEquals("Test Item 2", dao.get(2).first()?.name)
        Assert.assertEquals("Test Item 1", dao.get(1).first()?.name)
    }
}