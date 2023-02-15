package com.demo.minnies.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.minnies.shared.BuildConfig
import com.demo.minnies.shared.presentation.components.ActionBox
import com.demo.minnies.shared.presentation.components.OptionsBox
import com.demo.minnies.shared.presentation.components.SwitchBox
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.presentation.ui.oxfordBlue
import com.demo.minnies.shared.utils.Currency

const val LOGOUT_BUTTON_TEST_TAG = "LOGOUT_BUTTON_TEST_TAG"
const val ACCOUNT_BUTTON_TEST_TAG = "ACCOUNT_BUTTON_TEST_TAG"

@Composable
fun More(gotoAccountScreen: () -> Unit) {

    val viewModel = hiltViewModel<MoreScreenViewModel>()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState(true).value

    val notificationsPreference =
        viewModel.pushNotificationsPreference.collectAsState(initial = false).value

    val currencyPreference =
        viewModel.currencyPreference.collectAsState(initial = Currency.USD.name).value


    MoreScreen(
        isLoggedIn = isLoggedIn,
        logout = { viewModel.logout() },
        toggleNotifications = {
            viewModel.updatePushNotificationsPreference(it)
        },
        notificationsPreference = notificationsPreference,
        currencyPreference = currencyPreference,
        updateCurrency = {
            viewModel.updateCurrencyPreference(it)
        }, gotoAccountScreen = gotoAccountScreen
    )
}


@Composable
fun MoreScreen(
    isLoggedIn: Boolean,
    logout: () -> Unit,
    notificationsPreference: Boolean,
    currencyPreference: String,
    toggleNotifications: (Boolean) -> Unit,
    updateCurrency: (String) -> Unit,
    gotoAccountScreen: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(top = 30.dp)
    ) {

        if (isLoggedIn) {

            ActionBox(
                actionBox = ActionBox(
                    title = "Account",
                    subtitle = "Manage your profile",
                    icon = Icons.Default.ManageAccounts
                ) {
                    gotoAccountScreen()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .testTag(ACCOUNT_BUTTON_TEST_TAG)
            )

            Spacer(modifier = Modifier.height(5.dp))
            ActionBox(
                actionBox = ActionBox(
                    title = "Log out",
                    subtitle = "Go anonymous",
                    icon = Icons.Default.ExitToApp
                ) {
                    logout()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .testTag(LOGOUT_BUTTON_TEST_TAG)
            )
        }

//        Spacer(modifier = Modifier.height(5.dp))
//        SwitchBox(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight(),
//            switchBox = SwitchBox(
//                title = "Notifications",
//                subtitle = "Turn on or off app alerts",
//                action = {
//                    toggleNotifications(it)
//                },
//                checked = notificationsPreference,
//                icon = Icons.Outlined.Notifications
//            )
//        )


        Spacer(modifier = Modifier.height(5.dp))
        OptionsBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            optionsBox = OptionsBox(
                title = "Currency",
                subtitle = "Choose how you want to see prices",
                optionClickAction = {
                    updateCurrency(it)
                },
                icon = Icons.Default.CurrencyExchange,
                options = Currency.values().map { it.name },
                selectedValue = currencyPreference
            )
        )

    }
}


@Preview
@Composable
fun PreviewMoreScreen() {
    MinniesTheme {
        Column {
            MoreScreen(
                isLoggedIn = true,
                logout = {},
                toggleNotifications = {},
                notificationsPreference = true,
                currencyPreference = Currency.EUR.name,
                updateCurrency = {}, gotoAccountScreen = {}
            )

            MoreScreen(
                isLoggedIn = false,
                logout = {},
                toggleNotifications = {},
                notificationsPreference = false,
                currencyPreference = Currency.EUR.name,
                updateCurrency = {}, gotoAccountScreen = {}
            )
        }
    }
}