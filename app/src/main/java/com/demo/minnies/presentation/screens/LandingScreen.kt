package com.demo.minnies.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.minnies.auth.presentation.components.SignInPrompt
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.presentation.BottomNavigationDestination
import com.demo.minnies.shared.presentation.components.MinniesToolbar
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.utils.AuthScreen

const val NAVIGATION_BAR_TEST_TAG = "NAVIGATION_BAR_TEST_TAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    navController: NavHostController,
    loggedInUser: PartialUser? = null,
    navHost: @Composable (Modifier, NavHostController) -> Unit
) {

    //Base screens found on Bottom Navigation Bar
    val homeBottomNavigationDestinations = listOf(
        BottomNavigationDestination.Shop,
        BottomNavigationDestination.Search,
        BottomNavigationDestination.Orders,
        BottomNavigationDestination.Cart,
        BottomNavigationDestination.More
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val showLoginPromptState = rememberSaveable { (mutableStateOf(false)) }

    showBottomBarState.value =
        currentDestination?.route in homeBottomNavigationDestinations.map { it.route }


    showLoginPromptState.value = currentDestination?.route !in AuthScreen.values().map { it.name }

    val selectedItem by rememberSaveable { mutableStateOf(0) }

    Scaffold(bottomBar = {
        AnimatedVisibility(visible = showBottomBarState.value,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = ExitTransition.None,
            content = {
                NavigationBar(modifier = Modifier.testTag(NAVIGATION_BAR_TEST_TAG)) {

                    homeBottomNavigationDestinations.forEachIndexed { index, screen ->
                        NavigationBarItem(modifier = Modifier.semantics {
                            contentDescription = "${screen.title} page button"
                        },
                            icon = {
                                Icon(
                                    if (selectedItem == index) screen.selectedIcon else screen.icon,
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    }

                }
            })
    }, topBar = {
        Column {
            if (loggedInUser == null && showLoginPromptState.value) SignInPrompt(createAccount = {
                navController.navigate(AuthScreen.Register.name)
            }, login = {
                navController.navigate(AuthScreen.Login.name)
            })

            if (showBottomBarState.value.not())
                MinniesToolbar(
                    toolBarTitle = "", showNavigationIcon = true
                ) { navController.popBackStack() }
        }
    }) { innerPadding ->

        navHost(Modifier.padding(innerPadding), navController)
    }
}

@Preview
@Composable
fun PreviewLandingScreen() {
    MinniesTheme {
        LandingScreen(
            navController = rememberNavController(), loggedInUser = null
        ) { _, _ -> }
    }
}