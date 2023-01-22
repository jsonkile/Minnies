package com.demo.minnies.shop.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.demo.minnies.shop.presentation.screens.Product
import com.demo.minnies.shop.presentation.screens.Shop
import com.demo.minnies.shop.presentation.screens.ShopViewModel

fun NavGraphBuilder.shopGraph(navController: NavController) {
    navigation(startDestination = "shop-home", route = "shop") {
        composable("shop-home") {
            val shopViewModel = hiltViewModel<ShopViewModel>()
            Shop(title = "Shop", viewModel = shopViewModel) {
                navController.navigate("product/$it")
            }
        }

        composable(
            "product/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {

            Product()

        }
    }
}