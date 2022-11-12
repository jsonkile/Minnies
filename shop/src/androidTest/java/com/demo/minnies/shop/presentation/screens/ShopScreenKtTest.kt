package com.demo.minnies.shop.presentation.screens

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.demo.minnies.shop.data.fakeShopItemsDataSet
import com.demo.minnies.shop.presentation.models.toView
import org.junit.Rule
import org.junit.Test

class ShopScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenFeaturedItemsExists_FeaturedSectionShows() {

        val featuredItems =
            fakeShopItemsDataSet.filter { it.featured }.map { item ->
                item.toView().copy(image = "")
            }
        val itemsByCategories =
            fakeShopItemsDataSet.map { item ->
                item.toView().copy(image = "")
            }.groupBy { it.category }

        composeTestRule.setContent {
            ShopScreen(
                title = "Shop",
                shopItemsByCategory = itemsByCategories,
                featuredItems = featuredItems
            )
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