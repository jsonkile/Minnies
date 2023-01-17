package com.demo.minnies.shop.presentation.screens

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.data.fakeProductsDataSets
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.presentation.models.toView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class ShopViewModelTest {

    @Test
    fun testFeaturedItems() = runTest {

        val fetchFeaturedShopItemsUseCase = object : FetchFeaturedShopItemsUseCase {
            override fun invoke(): Flow<List<ViewProduct>> = flow {
                emit(
                    listOf(
                        ViewProduct(name = "jim", id = 1),
                        ViewProduct(name = "pam", id = 2)
                    )
                )
            }
        }

        val fetchShopItemsByCategoriesUseCase = object : FetchShopItemsByCategoriesUseCase {
            override fun invoke(): Flow<Map<Category, List<ViewProduct>>> = flow {
                val items = fakeProductsDataSets
                val currencyFormattedItems =
                    items.map { item -> item.toView(Currency.USD) }.groupBy { it.category }
                emit(currencyFormattedItems)
            }
        }

        val shopViewModel =
            ShopViewModel(fetchShopItemsByCategoriesUseCase, fetchFeaturedShopItemsUseCase)

        Assert.assertEquals(2, shopViewModel.featuredItems.first().size)
        Assert.assertEquals("jim", shopViewModel.featuredItems.first().first().name)
        Assert.assertEquals(2, shopViewModel.featuredItems.first()[1].id)

        Assert.assertTrue(shopViewModel.allItems.first().values.isNotEmpty())
    }
}