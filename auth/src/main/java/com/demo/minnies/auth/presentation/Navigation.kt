package com.demo.minnies.auth.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.demo.minnies.auth.presentation.screens.Login
import com.demo.minnies.auth.presentation.screens.Register
import com.demo.minnies.auth.presentation.screens.account.Account
import com.demo.minnies.shared.utils.AuthScreen
import timber.log.Timber
import java.util.Timer

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthScreen.Register.name, route = "auth") {
        composable(AuthScreen.Register.name) {
            Register(
                gotoLoginScreen = { navController.navigate(AuthScreen.Login.name) },
                goBack = { navController.popBackStack() }
            )
        }


        composable(AuthScreen.Login.name) {
            Login(
                gotoRegisterScreen = { navController.navigate(AuthScreen.Register.name) },
                goBack = {
                    navController.popBackStack()
                }
            )

        }

        composable(AuthScreen.Account.name) {
            Account()
        }
    }
}