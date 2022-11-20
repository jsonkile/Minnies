package com.demo.minnies.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.minnies.auth.presentation.components.SignInPrompt
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.presentation.Screen
import com.demo.minnies.shared.presentation.components.MinniesToolbar
import com.demo.minnies.shared.presentation.ui.MinniesTheme

const val NAVIGATION_BAR_TEST_TAG = "NAVIGATION_BAR_TEST_TAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    navController: NavHostController,
    loggedInUser: PartialUser? = null,
    navHost: @Composable (Modifier, NavHostController) -> Unit
) {

    //Base screens found on Bottom Navigation Bar
    val homeScreens = listOf(Screen.Shop, Screen.Orders, Screen.Cart)

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    when (currentDestination?.route) {
        in homeScreens.map { "${it.route}-home" } -> bottomBarState.value = true
        else -> bottomBarState.value = false
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
                    NavigationBar(modifier = Modifier.testTag(NAVIGATION_BAR_TEST_TAG)) {
                        var selectedItem by remember { mutableStateOf(0) }

                        homeScreens.forEachIndexed { index, screen ->
                            NavigationBarItem(
                                modifier = Modifier.semantics {
                                    contentDescription = "${screen.title} page button"
                                },
                                icon = {
                                    Icon(
                                        if (selectedItem == index) screen.selectedIcon else screen.icon,
                                        contentDescription = screen.title
                                    )
                                },
                                label = { Text(screen.title) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index

                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }

                    }
                })
        },
        topBar = {
            Column {
                if (loggedInUser == null)
                    SignInPrompt({}, {})

                if(bottomBarState.value.not())
                    MinniesToolbar(toolBarTitle = "") {
                        navController.popBackStack()
                    }
            }
        }
    ) { innerPadding ->

        navHost(Modifier.padding(innerPadding), navController)
    }
}

@Preview
@Composable
fun PreviewLandingScreen() {
    MinniesTheme {
        LandingScreen(
            navController = rememberNavController(),
            loggedInUser = null
        ) { _, _ -> }
    }
}