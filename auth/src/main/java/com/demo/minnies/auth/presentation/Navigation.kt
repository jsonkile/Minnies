package com.demo.minnies.auth.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.auth.presentation.screens.Register
import com.demo.minnies.auth.presentation.screens.Login

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthScreen.CreateAccount.name, route = "auth") {
        composable(AuthScreen.CreateAccount.name) {

            Register {
                navController.navigate(AuthScreen.Login.name)
            }

        }

        composable(AuthScreen.Login.name) {

            Login {
                navController.navigate(AuthScreen.CreateAccount.name)
            }

        }
    }
}

enum class AuthScreen {
    CreateAccount, Login
}