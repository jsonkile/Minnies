package com.demo.minnies.shop.presentation.screens

import app.cash.turbine.test
import com.demo.minnies.database.models.ProductCategory
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchProductsByCategoriesUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.util.mockProducts
import com.demo.minnies.shop.util.toView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test

class ShopViewModelTest {

    private val scheduler = TestCoroutineScheduler()

    @Test
    fun testUiState() = runTest {
        try {
            Dispatchers.setMain(UnconfinedTestDispatcher(scheduler))

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

            val fetchProductsByCategoriesUseCase = object : FetchProductsByCategoriesUseCase {
                override fun invoke(): Flow<Map<ProductCategory, List<ViewProduct>>> = flow {
                    val items = mockProducts
                    val currencyFormattedItems =
                        items.map { item -> item.toView(Currency.USD) }.groupBy { it.productCategory }
                    emit(currencyFormattedItems)
                }
            }

            val shopViewModel =
                ShopViewModel(fetchProductsByCategoriesUseCase, fetchFeaturedShopItemsUseCase)

            val collectJob = launch {
                shopViewModel.uiState.collect()
            }

            shopViewModel.uiState.test {
                val uiState = awaitItem() as ShopViewModel.UiState.Success

                Assert.assertEquals(2, uiState.featured.size)
                Assert.assertEquals("jim", uiState.featured.first().name)

                Assert.assertTrue(uiState.all.values.isNotEmpty())
            }

            collectJob.cancel()

        } finally {
            Dispatchers.resetMain()
        }
    }
}