package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.demo.minnies.database.models.ProductCategory
import com.demo.minnies.shop.presentation.models.ViewProduct

import org.junit.Rule
import org.junit.Test

class ProductCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenCardIsClickedOrTouched_Responds() {
        composeTestRule.setContent {
            ProductCard(
                viewProduct = ViewProduct(
                    name = "Test",
                    description = "Test",
                    productCategory = ProductCategory.Shorts,
                    image = "",
                    formattedPrice = "",
                    sizes = emptyList(),
                    id = 0
                ), modifier = Modifier.fillMaxSize().testTag(SHOP_ITEM_CARD_TEST_TAG)
            ) {}
        }

        composeTestRule.onNodeWithTag(SHOP_ITEM_CARD_TEST_TAG).assertHasClickAction()
    }

    @Test
    fun whenCardIsDisplayed_CorrectPriceAndNameShows() {
        composeTestRule.setContent {
            ProductCard(
                viewProduct = ViewProduct(
                    name = "Test Product",
                    description = "Test",
                    productCategory = ProductCategory.Shorts,
                    image = "",
                    formattedPrice = "$0.10",
                    sizes = emptyList(),
                    rating = 2.2,
                    id = 0
                ), modifier = Modifier.fillMaxSize()
            ) {}
        }

        composeTestRule.onNodeWithText("Test Product", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("$0.10", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun whenRatingIsNotBetween1and5_RatingSectionHides() {
        composeTestRule.setContent {
            ProductCard(
                viewProduct = ViewProduct(
                    name = "Test",
                    description = "Test",
                    productCategory = ProductCategory.Shorts,
                    image = "",
                    formattedPrice = "",
                    sizes = emptyList(),
                    id = 0
                ), modifier = Modifier.wrapContentSize()
            ) {}
        }

        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_ICON_TEST_TAG)
            .assertDoesNotExist()
    }

    @Test
    fun whenRatingIsBetween1and5_RatingSectionShows() {
        composeTestRule.setContent {
            ProductCard(
                viewProduct = ViewProduct(
                    name = "Test",
                    description = "Test",
                    productCategory = ProductCategory.Shorts,
                    image = "",
                    formattedPrice = "",
                    sizes = emptyList(),
                    rating = 1.5,
                    id = 0
                ), modifier = Modifier.fillMaxSize()
            ) {}
        }

        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_TEST_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(SHOP_ITEM_RATING_ICON_TEST_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}