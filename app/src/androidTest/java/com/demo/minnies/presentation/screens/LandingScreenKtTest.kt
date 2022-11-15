package com.demo.minnies.presentation.screens

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import com.demo.minnies.auth.presentation.components.SIGN_IN_PROMPT_TEST_TAG
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.presentation.Screen
import org.junit.Rule
import org.junit.Test


class LandingScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Test
    fun signInPromptShows_WhenNoLoggedInUser() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            LandingScreen(navController = navController, loggedInUser = null) { _, _ -> }
        }

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun signInPromptDoesNotShows_WhenLoggedInUser() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            LandingScreen(
                navController = navController,
                loggedInUser = PartialUser(fullName = "", emailAddress = "", phoneNumber = "")
            ) { _, _ -> }
        }

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun bottomBar_ShowAllScreenItems() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            LandingScreen(
                navController = navController,
                loggedInUser = PartialUser(fullName = "", emailAddress = "", phoneNumber = "")
            ) { _, _ -> }
        }

        composeTestRule.onAllNodesWithContentDescription(
            substring = true,
            ignoreCase = true,
            label = "page button"
        )
            .assertCountEquals(Screen::class.nestedClasses.size)
    }

    @Test
    fun bottomBar_FirstItemIsSelected() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            LandingScreen(
                navController = navController,
                loggedInUser = PartialUser(fullName = "", emailAddress = "", phoneNumber = "")
            ) { _, _ -> }
        }

        composeTestRule.onAllNodesWithContentDescription(
            substring = true,
            ignoreCase = true,
            label = "page button"
        ).onFirst().assertIsSelected()

        composeTestRule.onAllNodesWithContentDescription(
            substring = true,
            ignoreCase = true,
            label = "page button"
        ).onLast().assertIsNotSelected()
    }
}