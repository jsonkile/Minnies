package com.demo.minnies.orders.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.orders.presentation.screens.Orders
import com.demo.minnies.orders.presentation.screens.OrdersScreen
import com.demo.minnies.orders.presentation.screens.OrdersViewModel
import com.demo.minnies.shared.utils.OrdersScreen

fun NavGraphBuilder.ordersGraph(navController: NavController) {
    navigation(startDestination = OrdersScreen.OrdersHome.name, route = "orders") {
        composable(OrdersScreen.OrdersHome.name) {
            val viewModel = hiltViewModel<OrdersViewModel>()
            Orders(viewModel)
        }
    }
}
