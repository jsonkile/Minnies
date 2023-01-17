package com.demo.minnies.cart.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.cart.presentation.screen.CartScreen

fun NavGraphBuilder.cartGraph(navController: NavController) {
    navigation(startDestination = "cart-home", route = "cart") {
        composable("cart-home") {
            CartScreen()
        }
    }
}