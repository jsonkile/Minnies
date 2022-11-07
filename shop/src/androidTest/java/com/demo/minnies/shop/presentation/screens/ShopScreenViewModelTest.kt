package com.demo.minnies.shop.presentation.screens

import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ShopScreenViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: ShopScreenViewModel

    @Inject
    lateinit var fetchShopItemsByCategoriesUseCase: FetchShopItemsByCategoriesUseCase

    @Inject
    lateinit var fetchFeaturedShopItemsUseCase: FetchFeaturedShopItemsUseCase

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel =
            ShopScreenViewModel(fetchShopItemsByCategoriesUseCase, fetchFeaturedShopItemsUseCase)
    }


    @Test
    fun checkShopItemsByCategory_ReturnsData() = runTest {
        val items = viewModel.shopItemsByCategories.first()
        assertEquals(items.isNotEmpty(), true)
    }

    @Test
    fun checkFeaturedItems_ReturnsData() = runTest {
        val items = viewModel.featuredItems.first()
        assertEquals(items.isNotEmpty(), true)
    }
}