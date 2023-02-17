package com.demo.minnies.shop.presentation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.demo.minnies.shared.utils.CartScreen
import com.demo.minnies.shared.utils.ProductScreen
import com.demo.minnies.shop.presentation.screens.Product
import com.demo.minnies.shop.presentation.screens.Search
import com.demo.minnies.shop.presentation.screens.Shop
import com.demo.minnies.shop.presentation.screens.ShopViewModel


fun NavGraphBuilder.shopGraph(navController: NavController) {
    navigation(
        startDestination = ProductScreen.Shop.name,
        route = ProductScreen::class.simpleName.orEmpty()
    ) {
        composable(ProductScreen.Shop.name) {
            val viewModel = hiltViewModel<ShopViewModel>()
            Shop(
                viewModel = viewModel,
                navigateToProduct = { navController.navigate("${ProductScreen.Product.name}/$it") })
        }

        composable(ProductScreen.Search.name) {
            Search(navigateToProduct = {
                navController.navigate("${ProductScreen.Product.name}/$it")
            })
        }

        composable(
            "${ProductScreen.Product.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            Product(gotoCartAction = {
                navController.navigate(CartScreen::class.simpleName.orEmpty()) {
                    navController.previousBackStackEntry?.destination?.let { destination ->
                        popUpTo(destination.route.orEmpty()) {
                            saveState = true
                        }
                    }
                    launchSingleTop = false
                    restoreState = true
                }
            })
        }
    }
}

