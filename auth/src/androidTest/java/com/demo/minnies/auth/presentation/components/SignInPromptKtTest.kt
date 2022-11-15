package com.demo.minnies.auth.presentation.components

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class SignInPromptKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun signInPromptButtonsShows_AndCanBeClicked() {
        composeTestRule.setContent {
            SignInPrompt({}, {})
        }

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG).assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT__LOGIN_BUTTON_TEST_TAG).assertIsDisplayed()
            .assertHasClickAction()
    }
}