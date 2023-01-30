package com.demo.minnies.orders.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.demo.minnies.orders.presentation.screens.home.Orders
import com.demo.minnies.orders.presentation.screens.home.OrdersViewModel
import com.demo.minnies.orders.presentation.screens.order.Order
import com.demo.minnies.orders.presentation.screens.order.OrderViewModel
import com.demo.minnies.shared.utils.OrdersScreen

fun NavGraphBuilder.ordersGraph(navController: NavController) {
    navigation(startDestination = OrdersScreen.OrdersHome.name, route = "orders") {
        composable(OrdersScreen.OrdersHome.name) {
            val viewModel = hiltViewModel<OrdersViewModel>()
            Orders(viewModel) { ref -> navController.navigate("${OrdersScreen.Order.name}/$ref") }
        }

        composable(
            "${OrdersScreen.Order.name}/{ref}",
            listOf(navArgument("ref") { type = NavType.StringType })
        ) {
            val viewModel = hiltViewModel<OrderViewModel>()
            Order(viewModel)
        }
    }
}
