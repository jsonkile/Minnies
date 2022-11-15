package com.demo.minnies.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.demo.minnies.auth.presentation.components.SignInPrompt
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.presentation.Screen
import com.demo.minnies.shared.ui.MinniesTheme

const val NAVIGATION_BAR_TEST_TAG = "NAVIGATION_BAR_TEST_TAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    navController: NavHostController,
    loggedInUser: PartialUser? = null,
    navHost: @Composable (Modifier, NavHostController) -> Unit
) {

    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.testTag(NAVIGATION_BAR_TEST_TAG)) {
                val items = listOf(Screen.Home, Screen.Orders, Screen.Cart)
                var selectedItem by remember { mutableStateOf(0) }

                items.forEachIndexed { index, screen ->
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
        },
        topBar = {
            if (loggedInUser == null)
                SignInPrompt({}, {})
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