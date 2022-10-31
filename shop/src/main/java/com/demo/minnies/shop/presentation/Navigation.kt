package com.demo.minnies.shop.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.shop.presentation.screen.ShopScreen

fun NavGraphBuilder.shopGraph(navController: NavController) {
    navigation(startDestination = "shop", route = "home") {
        composable("shop") {
            ShopScreen(title = "Shop")
        }
    }
}