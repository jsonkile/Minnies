package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import com.demo.minnies.shop.data.fakeProductsDataSets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class ProductsRepoMockImplTest {

    private lateinit var shopRepoMockImpl: ProductsRepoMockImpl

    @Before
    fun setup() {
        shopRepoMockImpl = ProductsRepoMockImpl()
    }


    @Test
    fun `test that getAllItems() returns items`() = runTest {
        Assert.assertEquals(fakeProductsDataSets.size, shopRepoMockImpl.getAllItems().first().size)
    }

    @Test
    fun `test that addItem() successfully adds item to list`() = runTest {
        Assert.assertEquals(fakeProductsDataSets.size, shopRepoMockImpl.getAllItems().first().size)

        shopRepoMockImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals(
            fakeProductsDataSets.size + 1,
            shopRepoMockImpl.getAllItems().first().size
        )
    }

    @Test
    fun `test that getProductById() returns correct product`() = runTest {
        shopRepoMockImpl.addItem(
            Product(
                id = 200,
                name = "New Product",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals("New Product", shopRepoMockImpl.getProductById(200).first()?.name)
    }

    @Test
    fun `test that getFeaturedItems() returns featured items`() = runTest {

        val currentFeaturedListSize = shopRepoMockImpl.getFeaturedItems().first().size

        shopRepoMockImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0,
                featured = true
            )
        )

        shopRepoMockImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0,
                featured = false
            )
        )

        shopRepoMockImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0,
                featured = true
            )
        )

        Assert.assertEquals(
            currentFeaturedListSize + 2,
            shopRepoMockImpl.getFeaturedItems().first().size
        )
    }


    @Test
    fun `test that getItemsByCategory() returns correct items`() = runTest {

        val currentTopsListSize = shopRepoMockImpl.getItemsByCategory(Category.Top).first().size

        shopRepoMockImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Top,
                price = 0.0,
                featured = true
            )
        )

        shopRepoMockImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Top,
                price = 0.0,
                featured = false
            )
        )

        shopRepoMockImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0,
                featured = true
            )
        )

        Assert.assertEquals(
            currentTopsListSize + 2,
            shopRepoMockImpl.getItemsByCategory(Category.Top).first().size
        )
    }
}