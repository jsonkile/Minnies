package com.demo.minnies.shop.presentation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import com.demo.minnies.shared.DummyActivity
import com.demo.minnies.shared.utils.ProductScreen
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
    val composeTestRule = createAndroidComposeRule<DummyActivity>()

    private lateinit var navController: TestNavHostController

    @Test
    fun onNavigationToProductScreen_IDIsParsedCorrectly() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            NavHost(navController = navController, startDestination = ProductScreen::class.simpleName.orEmpty()) {
                shopGraph(navController)
            }

            navController.navigate("${ProductScreen.Product.name}/2")
        }

        Assert.assertEquals(
            "${ProductScreen.Product.name}/{id}", navController.currentDestination?.route
        )

        Assert.assertEquals(
            NavArgument.Builder().setType(NavType.IntType).setIsNullable(false).build(),
            navController.currentDestination?.arguments?.getValue("id")
        )
    }

}