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
import com.demo.minnies.auth.presentation.AuthScreen
import com.demo.minnies.auth.presentation.authGraph
import com.demo.minnies.orders.presentation.ordersGraph
import com.demo.minnies.presentation.screens.More
import com.demo.minnies.shop.presentation.cartGraph
import com.demo.minnies.shop.presentation.screens.Search
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

        composable(Screen.Search.route) {
            Search(navigateToProduct = {
                navController.navigate("product/$it")
            })
        }

        ordersGraph(navController)

        cartGraph(navController)

        authGraph(navController)

        composable(Screen.More.route) {
            More(gotoAccountScreen = {
                navController.navigate(AuthScreen.Account.name)
            })
        }
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

    object Search : Screen(
        route = "search",
        title = "Search",
        icon = Icons.Outlined.Search,
        selectedIcon = Icons.Default.Search
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

    object More : Screen(
        route = "more",
        title = "More",
        icon = Icons.Outlined.MoreHoriz,
        selectedIcon = Icons.Filled.MoreHoriz
    )
}