package com.demo.minnies.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.demo.minnies.cart.presentation.cartGraph
import com.demo.minnies.orders.presentation.ordersGraph
import com.demo.minnies.shop.presentation.shopGraph

@Composable
fun MinniesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "shop"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        shopGraph(navController)

        ordersGraph(navController)

        cartGraph(navController)
    }
}


sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Shop : Screen(
        route = "shop",
        title = "Shop",
        icon = Icons.Outlined.DryCleaning,
        selectedIcon = Icons.Filled.DryCleaning
    )

    object Orders : Screen(
        route = "orders",
        title = "Orders",
        icon = Icons.Outlined.ShoppingBag,
        selectedIcon = Icons.Filled.ShoppingBag
    )

    object Cart : Screen(
        route = "cart",
        title = "Cart",
        icon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart
    )
}