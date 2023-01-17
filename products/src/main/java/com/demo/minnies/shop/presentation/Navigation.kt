package com.demo.minnies.shop.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.demo.minnies.auth.presentation.AuthScreen
import com.demo.minnies.shop.presentation.screens.*
import com.demo.minnies.shop.presentation.screens.cart.Cart
import com.demo.minnies.shop.presentation.screens.cart.CartViewModel
import com.demo.minnies.shop.presentation.screens.checkout.Checkout
import com.demo.minnies.shop.presentation.screens.checkout.CheckoutViewModel

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


fun NavGraphBuilder.cartGraph(navController: NavController) {
    navigation(startDestination = "cart-home", route = "cart") {
        composable("cart-home") {
            val cartViewModel = hiltViewModel<CartViewModel>()
            Cart(
                viewModel = cartViewModel,
                gotoCheckoutScreen = { navController.navigate("checkout") },
                gotoProductScreen = { productId ->
                    navController.navigate("product/$productId")
                })
        }

        composable("checkout") {
            val checkoutViewModel = hiltViewModel<CheckoutViewModel>()
            Checkout(
                viewModel = checkoutViewModel,
                gotoAccountScreen = { navController.navigate(AuthScreen.Account.name) })
        }
    }
}