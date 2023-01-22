package com.demo.minnies.cart.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.auth.presentation.AuthScreen
import com.demo.minnies.cart.presentation.screens.cart.Cart
import com.demo.minnies.cart.presentation.screens.cart.CartViewModel
import com.demo.minnies.cart.presentation.screens.checkout.Checkout
import com.demo.minnies.cart.presentation.screens.checkout.CheckoutViewModel

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