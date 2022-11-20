package com.demo.minnies.shop.presentation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationKtTest {

    @get:Rule(order = 1)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavHost(navController = navController, startDestination = "shop"){
                shopGraph(navController)
            }
        }

        hiltAndroidRule.inject()
    }

    @Test
    fun onNavigationToProductScreen_IDIsParsedCorrectly() {
        navController.navigate("product/2")
        Assert.assertEquals("product/2", navController.currentDestination?.route)
        Assert.assertEquals("2", navController.currentDestination?.arguments?.get("id"))
    }

}