package com.demo.minnies.shop.presentation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.shop.presentation.screens.ShopScreen
import com.demo.minnies.shop.presentation.screens.ShopScreenViewModel

fun NavGraphBuilder.shopGraph(navController: NavController) {
    navigation(startDestination = "shop", route = "home") {
        composable("shop") {
            val viewModel: ShopScreenViewModel = hiltViewModel()

            val shopItemsByCategory =
                viewModel.shopItemsByCategories.collectAsState(initial = emptyMap()).value
            val featuredItems = viewModel.featuredItems.collectAsState(initial = emptyList()).value

            ShopScreen(
                title = "Shop",
                featuredItems = featuredItems,
                shopItemsByCategory = shopItemsByCategory
            )
        }
    }
}