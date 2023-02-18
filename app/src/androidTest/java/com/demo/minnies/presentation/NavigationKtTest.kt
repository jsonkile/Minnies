package com.demo.minnies.presentation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.demo.minnies.shared.DummyActivity
import com.demo.minnies.shared.utils.ProductScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationKtTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<DummyActivity>()

    lateinit var navController: TestNavHostController

    @Test
    fun minniesNavHost_verifyStartDestination() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MinniesNavHost(navController = navController)
        }

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(ProductScreen.Shop.name, route)
    }

    @Test
    fun minniesNavHost_verifyRootNodesCount() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MinniesNavHost(navController = navController)
        }

        val nodesCount = navController.graph.nodes.size()
        assertEquals(BottomNavigationDestination::class.nestedClasses.size, nodesCount)
    }
}