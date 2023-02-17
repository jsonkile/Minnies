package com.demo.minnies.cart.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.cart.presentation.screens.cart.Cart
import com.demo.minnies.cart.presentation.screens.cart.CartViewModel
import com.demo.minnies.cart.presentation.screens.checkout.Checkout
import com.demo.minnies.cart.presentation.screens.checkout.CheckoutViewModel
import com.demo.minnies.shared.utils.AuthScreen
import com.demo.minnies.shared.utils.CartScreen
import com.demo.minnies.shared.utils.OrdersScreen
import com.demo.minnies.shared.utils.ProductScreen

fun NavGraphBuilder.cartGraph(navController: NavController) {
    navigation(
        startDestination = CartScreen.CartHome.name,
        route = CartScreen::class.simpleName.orEmpty()
    ) {
        composable(CartScreen.CartHome.name) {
            val cartViewModel = hiltViewModel<CartViewModel>()
            Cart(
                viewModel = cartViewModel,
                gotoCheckoutScreen = { navController.navigate(CartScreen.Checkout.name) },
                gotoProductScreen = { productId ->
                    navController.navigate("${ProductScreen.Product.name}/$productId")
                })
        }

        composable(CartScreen.Checkout.name) {
            val checkoutViewModel = hiltViewModel<CheckoutViewModel>()
            Checkout(
                viewModel = checkoutViewModel,
                gotoAccountScreen = { navController.navigate(AuthScreen.Account.name) },
                gotoOrdersScreen = { navController.navigate(OrdersScreen.OrdersHome.name) })
        }
    }
}