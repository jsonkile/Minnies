package com.demo.minnies.orders.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.orders.presentation.screens.MyOrdersScreen

fun NavGraphBuilder.ordersGraph(navController: NavController) {
    navigation(startDestination = "orders-home", route = "orders") {
        composable("orders-home") {
            MyOrdersScreen(title = "Orders")
        }
    }
}