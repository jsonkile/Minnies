package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.models.Product
import com.demo.minnies.database.models.ProductCategory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class ProductsRepoImplTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var productsRepoRoomImpl: ProductsRepoRoomImpl

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }


    @Test
    fun getAllItems_ReturnsAllItems() = runTest {

        val currentSize = productsRepoRoomImpl.getAllItems().first().size

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0
            )
        )

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals(currentSize + 2, productsRepoRoomImpl.getAllItems().first().size)
    }

    @Test
    fun addItem_SuccessfullyAddsItem() = runTest {

        val currentSize = productsRepoRoomImpl.getAllItems().first().size

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0
            )
        )

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals(currentSize + 2, productsRepoRoomImpl.getAllItems().first().size)
    }

    @Test
    fun getProductById_ReturnsCorrectProduct() = runTest {

        val id = productsRepoRoomImpl.addItem(
            Product(
                name = "Happy",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals("Happy", productsRepoRoomImpl.getProductById(id.toInt()).first()?.name)
    }

    @Test
    fun getFeaturedItems_ReturnsFeaturedItems() = runTest {

        val currentFeaturedListSize = productsRepoRoomImpl.getFeaturedItems().first().size

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0,
                featured = true
            )
        )

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0,
                featured = false
            )
        )

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0,
                featured = true
            )
        )

        Assert.assertEquals(
            currentFeaturedListSize + 2,
            productsRepoRoomImpl.getFeaturedItems().first().size
        )
    }


    @Test
    fun getItemsByCategory_ReturnsCorrectItems() = runTest {

        val currentTopsListSize = productsRepoRoomImpl.getItemsByCategory(ProductCategory.Top).first().size

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Top,
                price = 0.0,
                featured = true
            )
        )

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Top,
                price = 0.0,
                featured = false
            )
        )

        productsRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                productCategory = ProductCategory.Kicks,
                price = 0.0,
                featured = true
            )
        )

        Assert.assertEquals(
            currentTopsListSize + 2,
            productsRepoRoomImpl.getItemsByCategory(ProductCategory.Top).first().size
        )
    }
}