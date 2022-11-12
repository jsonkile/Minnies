package com.demo.minnies.shop.presentation.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.presentation.models.ViewShopItem

import org.junit.Rule
import org.junit.Test

class ShopItemCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenCardIsClickedOrTouched_Responds() {
        composeTestRule.setContent {
            ShopItemCard(
                viewShopItem = ViewShopItem(
                    name = "Test",
                    description = "Test",
                    category = Category.Shorts,
                    image = "",
                    price = "",
                    sizes = emptyList(),
                    id = 0
                )
            )
        }

        composeTestRule.onNodeWithTag(SHOP_ITEM_CARD_TEST_TAG).assertHasClickAction()
    }

    @Test
    fun whenCardIsDisplayed_CorrectPriceAndNameShows() {
        composeTestRule.setContent {
            ShopItemCard(
                viewShopItem = ViewShopItem(
                    name = "Test Product",
                    description = "Test",
                    category = Category.Shorts,
                    image = "",
                    price = "$0.10",
                    sizes = emptyList(),
                    rating = 2.2,
                    id = 0
                )
            )
        }

        composeTestRule.onNodeWithText("Test Product", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("$0.10", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun whenRatingIsNotBetween1and5_RatingSectionHides() {
        composeTestRule.setContent {
            ShopItemCard(
                viewShopItem = ViewShopItem(
                    name = "Test",
                    description = "Test",
                    category = Category.Shorts,
                    image = "",
                    price = "",
                    sizes = emptyList(),
                    id = 0
                )
            )
        }

        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_ICON_TEST_TAG)
            .assertDoesNotExist()
    }

    @Test
    fun whenRatingIsBetween1and5_RatingSectionShows() {
        composeTestRule.setContent {
            ShopItemCard(
                viewShopItem = ViewShopItem(
                    name = "Test",
                    description = "Test",
                    category = Category.Shorts,
                    image = "",
                    price = "",
                    sizes = emptyList(),
                    rating = 1.5,
                    id = 0
                )
            )
        }

        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_TEST_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_ICON_TEST_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}