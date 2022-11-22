package com.demo.minnies.shop.presentation.screens

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.demo.minnies.shared.utils.CustomExceptions
import com.demo.minnies.shop.presentation.models.ViewProduct
import org.junit.Rule
import org.junit.Test

internal class ProductScreenKtTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun uiState_isLoading_showOnlyLoadingComponents() {
        composeTestRule.setContent {
            ProductScreen(uiState = ProductViewModel.UiState.Loading)
        }

        composeTestRule.onNodeWithTag(ERROR_AREA_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(PRODUCT_DETAILS_AREA_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LOADING_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun uiState_isSuccess_showOnlyProductDetailsComponents() {
        composeTestRule.setContent {
            ProductScreen(uiState = ProductViewModel.UiState.Success(ViewProduct()))
        }

        composeTestRule.onNodeWithTag(ERROR_AREA_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(PRODUCT_DETAILS_AREA_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LOADING_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun uiState_isError_showOnlyErrorView() {
        composeTestRule.setContent {
            ProductScreen(
                uiState = ProductViewModel.UiState.Error(
                    CustomExceptions.NotFoundException(
                        message = "human"
                    )
                )
            )
        }

        composeTestRule.onNodeWithTag(ERROR_AREA_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "human").assertIsDisplayed()
        composeTestRule.onNodeWithTag(PRODUCT_DETAILS_AREA_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LOADING_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun addToCartButton_IsVisibleAndClickable_WhenUiStateIsSuccess() {
        composeTestRule.setContent {
            ProductScreen(uiState = ProductViewModel.UiState.Success(ViewProduct()))
        }

        composeTestRule.onNodeWithTag(ADD_TO_CART_BUTTON_TEST_TAG).assertHasClickAction()
        composeTestRule.onNodeWithTag(ADD_TO_CART_BUTTON_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun price_IsVisible_WhenUiStateIsSuccess() {
        composeTestRule.setContent {
            ProductScreen(uiState = ProductViewModel.UiState.Success(ViewProduct(price = "$7")))
        }

        composeTestRule.onNodeWithTag(PRICE_TEST_TAG).assertIsDisplayed()
    }
}