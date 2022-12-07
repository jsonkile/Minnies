package com.demo.minnies.auth.presentation.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test


internal class LoginKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenUiState_IsError_ErrorBarShow() {
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginViewModel.UiState.Error(throwable = Throwable("error")),
                login = { _, _ -> })
        }

        composeTestRule.onNodeWithTag(LOGIN_ERROR_BAR_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun whenUiState_IsLoading_ButtonIndicates() {
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginViewModel.UiState.Loading,
                login = { _, _ -> })
        }

        composeTestRule.onNodeWithText(LOADING_TEXT).assertIsDisplayed()
    }

    @Test
    fun whenUiState_IsDefault_ErrorBarAndLoadingHides() {
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginViewModel.UiState.Default,
                login = { _, _ -> })
        }

        composeTestRule.onNodeWithText(LOADING_TEXT).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LOGIN_ERROR_BAR_TEST_TAG).assertDoesNotExist()
    }

}