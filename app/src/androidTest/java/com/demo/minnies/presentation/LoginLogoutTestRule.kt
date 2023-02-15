package com.demo.minnies.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.demo.minnies.auth.presentation.components.SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG
import com.demo.minnies.auth.presentation.screens.*
import com.demo.minnies.presentation.screens.LOGOUT_BUTTON_TEST_TAG
import com.demo.minnies.shared.utils.getRandomString
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class LoginLogoutTestRule(private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) :
    TestRule {

    override fun apply(base: Statement, description: Description): Statement =
        MyStatement(base, rule)

    class MyStatement(
        private val base: Statement,
        private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
    ) : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG).performClick()

            composeTestRule.onNodeWithTag(FULL_NAME_TEXT_FIELD_TEST_TAG)
                .performTextInput("Jayson Kile")
            val email = "${getRandomString(10)}@email.com"
            val phone = "90774343434"
            composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_TEST_TAG)
                .performTextInput(email)
            composeTestRule.onNodeWithTag(PHONE_TEXT_FIELD_TEST_TAG).performTextInput(phone)
            composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD_TEST_TAG).performTextInput("Test@123")
            composeTestRule.onNodeWithTag(CONFIRM_PASSWORD_TEXT_FIELD_TEST_TAG)
                .performTextInput("Test@123")

            composeTestRule.onNodeWithTag(REGISTER_BUTTON_TEST_TAG).performClick()

            composeTestRule.waitUntil(3000) {
                composeTestRule.onAllNodesWithTag(REGISTER_SCREEN_CONTAINER_TEST_TAG)
                    .fetchSemanticsNodes()
                    .isEmpty()
            }

            try {
                base.evaluate()
            } finally {
                composeTestRule.onNodeWithContentDescription("More page button").performClick()

                composeTestRule.onNodeWithTag(LOGOUT_BUTTON_TEST_TAG).performClick()
            }
        }
    }
}