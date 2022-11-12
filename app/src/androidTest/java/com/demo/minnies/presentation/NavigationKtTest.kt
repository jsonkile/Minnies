package com.demo.minnies.presentation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @org.junit.Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MinniesNavHost(navController = navController)
        }
    }

    @Test
    fun minniesNavHost_verifyStartDestination() {
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals("shop", route)
    }

    @Test
    fun minniesNavHost_verifyRootNodesCount() {
        val nodesCount = navController.graph.nodes.size()
        assertEquals(Screen::class.nestedClasses.size, nodesCount)
    }
}