package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.models.ProductCategory
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shop.util.mockProducts
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

internal class FetchProductsByCategoriesUseCaseImplTest {


    @Test
    fun `test that items are returned by categories`() = runTest {
        val getUserCurrencyPreferenceUseCase = object : GetUserCurrencyPreferenceUseCase {
            override fun invoke() = flow { emit("USD") }
        }

        val getAllProductsUseCase = object : GetAllProductsUseCase {
            override fun invoke() = flow { emit(mockProducts) }
        }

        val fetchShopItemsByCategoriesUseCase = FetchProductsByCategoriesUseCaseImpl(
            getAllProductsUseCase,
            getUserCurrencyPreferenceUseCase
        )

        val items = fetchShopItemsByCategoriesUseCase().first()
        Assert.assertEquals(true, items[ProductCategory.Top]?.isNotEmpty())
        Assert.assertEquals(true, items[ProductCategory.Kicks]?.isNotEmpty())
        Assert.assertEquals(true, items[ProductCategory.Accessory]?.isNotEmpty())
        Assert.assertEquals(true, items[ProductCategory.Shorts]?.isNotEmpty())
    }
}