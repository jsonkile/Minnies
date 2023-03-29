package com.demo.minnies.shop.presentation.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
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
            mockProducts.filter { it.featured }.mapIndexed { index, item ->
                item.toView(Currency.USD).copy(image = "", id = index)
            }

        val itemsByCategories =
            mockProducts.mapIndexed { index, item ->
                item.toView(Currency.USD).copy(image = "", id = index)
            }.groupBy { it.productCategory }

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

        composeTestRule.onNodeWithTag(SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG)
            .performScrollToIndex(featuredItems.size - 1).apply {
                printToLog("TAG")
                onChildren().assertAny(hasText(featuredItems.last().name))
            }
    }
}