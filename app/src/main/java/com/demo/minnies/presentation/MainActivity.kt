package com.demo.minnies.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.minnies.presentation.screens.LandingScreen
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.utils.AuthScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val navController = rememberNavController()
            val systemUiController = rememberSystemUiController()

            val mainActivityViewModel = hiltViewModel<MainActivityViewModel>()

            val loggedInUser =
                mainActivityViewModel.loggedInUser.collectAsState(initial = null).value

            val currentDestination = navController.currentBackStackEntryAsState().value?.destination

            MinniesTheme {

                val statusBarColor = when {
                    loggedInUser == null && currentDestination?.route !in AuthScreen.values()
                        .map { it.name } -> MaterialTheme.colorScheme.tertiaryContainer
                    else -> MaterialTheme.colorScheme.background
                }

                systemUiController.setStatusBarColor(color = statusBarColor)

                systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))

                LandingScreen(
                    navController = navController,
                    loggedInUser = loggedInUser,
                    navHost = { modifier, navController ->
                        MinniesNavHost(navController = navController, modifier = modifier)
                    })
            }

        }
    }
}