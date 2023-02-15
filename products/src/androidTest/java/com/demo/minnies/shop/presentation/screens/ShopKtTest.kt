package com.demo.minnies.shop.presentation.screens

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.util.mockProducts
import com.demo.minnies.shop.util.toView
import org.junit.Rule
import org.junit.Test

class ShopKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenFeaturedItemsExists_FeaturedSectionShows() {

        val featuredItems =
            mockProducts.filter { it.featured }.map { item ->
                item.toView(Currency.USD).copy(image = "")
            }

        val itemsByCategories =
            mockProducts.map { item ->
                item.toView(Currency.USD).copy(image = "")
            }.groupBy { it.category }

        composeTestRule.setContent {
            ShopScreen(
                ShopViewModel.UiState.Success(
                    all = itemsByCategories,
                    featured = featuredItems
                )
            ) {}
        }


        composeTestRule.onNodeWithTag(SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG)
            .apply { if (featuredItems.isEmpty()) assertDoesNotExist() else assertIsDisplayed() }

        composeTestRule.onNodeWithTag(testTag = SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG).apply {
            if (featuredItems.isEmpty()) assertDoesNotExist() else assertIsDisplayed()
        }

        composeTestRule.onNodeWithTag(SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG).onChildren()
            .assertCountEquals(featuredItems.size)
    }
}