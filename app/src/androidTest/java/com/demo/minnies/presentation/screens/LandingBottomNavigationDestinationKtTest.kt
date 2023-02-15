package com.demo.minnies.presentation.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.demo.minnies.presentation.BottomNavigationDestination
import com.demo.minnies.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LandingBottomNavigationDestinationKtTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testBottomNav() = runTest {
        composeTestRule.onAllNodesWithContentDescription(
            substring = true,
            ignoreCase = true,
            label = "page button"
        )
            .assertCountEquals(BottomNavigationDestination::class.nestedClasses.size)

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