package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import com.demo.minnies.shop.data.fakeProductsDataSets
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
internal class ShopRepoRoomImplTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var shopRepoRoomImpl: ShopRepoRoomImpl

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }


    @Test
    fun getAllItems_ReturnsAllItems() = runTest {

        val currentSize = shopRepoRoomImpl.getAllItems().first().size

        shopRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0
            )
        )

        shopRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals(currentSize + 2, shopRepoRoomImpl.getAllItems().first().size)
    }

    @Test
    fun addItem_SuccessfullyAddsItem() = runTest {

        val currentSize = shopRepoRoomImpl.getAllItems().first().size

        shopRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0
            )
        )

        shopRepoRoomImpl.addItem(
            Product(
                name = "",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals(currentSize + 2, shopRepoRoomImpl.getAllItems().first().size)
    }

    @Test
    fun getProductById_ReturnsCorrectProduct() = runTest {

        val id = shopRepoRoomImpl.addItem(
            Product(
                name = "Happy",
                image = "",
                description = "",
                sizes = emptyList(),
                category = Category.Kicks,
                price = 0.0
            )
        )

        Assert.assertEquals("Happy", shopRepoRoomImpl.getProductById(id.toInt()).first()?.name)
    }

    @Test
    fun getFeaturedItems_ReturnsFeaturedItems() = runTest {

        val currentFeaturedListSize = shopRepoRoomImpl.getFeaturedItems().first().size

        shopRepoRoomImpl.addItem(
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

        shopRepoRoomImpl.addItem(
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

        shopRepoRoomImpl.addItem(
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
            shopRepoRoomImpl.getFeaturedItems().first().size
        )
    }


    @Test
    fun getItemsByCategory_ReturnsCorrectItems() = runTest {

        val currentTopsListSize = shopRepoRoomImpl.getItemsByCategory(Category.Top).first().size

        shopRepoRoomImpl.addItem(
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

        shopRepoRoomImpl.addItem(
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

        shopRepoRoomImpl.addItem(
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
            shopRepoRoomImpl.getItemsByCategory(Category.Top).first().size
        )
    }
}