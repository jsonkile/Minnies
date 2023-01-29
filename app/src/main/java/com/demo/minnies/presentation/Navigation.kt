package com.demo.minnies.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.demo.minnies.auth.presentation.authGraph
import com.demo.minnies.cart.presentation.cartGraph
import com.demo.minnies.orders.presentation.ordersGraph
import com.demo.minnies.presentation.screens.More
import com.demo.minnies.shared.utils.AuthScreen
import com.demo.minnies.shared.utils.CartScreen
import com.demo.minnies.shared.utils.OrdersScreen
import com.demo.minnies.shared.utils.ProductScreen
import com.demo.minnies.shop.presentation.shopGraph

@Composable
fun MinniesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "products"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        shopGraph(navController)

        ordersGraph(navController)

        cartGraph(navController)

        authGraph(navController)

        composable(BottomNavigationDestination.More.route) {
            More(gotoAccountScreen = {
                navController.navigate(AuthScreen.Account.name)
            })
        }
    }
}


sealed class BottomNavigationDestination(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Shop : BottomNavigationDestination(
        route = ProductScreen.Shop.name,
        title = "Shop",
        icon = Icons.Outlined.DryCleaning,
        selectedIcon = Icons.Filled.DryCleaning
    )

    object Search : BottomNavigationDestination(
        route = ProductScreen.Search.name,
        title = "Search",
        icon = Icons.Outlined.Search,
        selectedIcon = Icons.Default.Search
    )

    object Orders : BottomNavigationDestination(
        route = OrdersScreen.OrdersHome.name,
        title = "Orders",
        icon = Icons.Outlined.ShoppingBag,
        selectedIcon = Icons.Filled.ShoppingBag
    )

    object Cart : BottomNavigationDestination(
        route = CartScreen.CartHome.name,
        title = "Cart",
        icon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart
    )

    object More : BottomNavigationDestination(
        route = "more",
        title = "More",
        icon = Icons.Outlined.MoreHoriz,
        selectedIcon = Icons.Filled.MoreHoriz
    )
}