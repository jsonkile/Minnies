package com.demo.minnies.shop.presentation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.shop.domain.usescases.FetchAndGroupShopItemsUseCase
import com.demo.minnies.shop.presentation.screens.ShopScreen
import com.demo.minnies.shop.presentation.screens.ShopScreenViewModel
import com.demo.minnies.shop.util.forView
import kotlinx.coroutines.flow.map

fun NavGraphBuilder.shopGraph(navController: NavController) {
    navigation(startDestination = "shop", route = "home") {
        composable("shop") {
            val viewModel: ShopScreenViewModel = hiltViewModel()
            val shopItems = viewModel.shopItems.collectAsState(initial = emptyMap()).value
            ShopScreen(title = "Shop", shopItems)
        }
    }
}