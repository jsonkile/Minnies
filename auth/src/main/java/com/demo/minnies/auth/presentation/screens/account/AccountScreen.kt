package com.demo.minnies.auth.presentation.screens.account

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.minnies.shared.presentation.components.MinniesOutlinedDefaultButton
import com.demo.minnies.shared.presentation.components.TextWithIcon
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun Account() {

    val viewModel = hiltViewModel<AccountViewModel>()
    val uiState = viewModel.uiState.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    AccountScreen(
        uiState = uiState,
        scaffoldState = scaffoldState,
        updateShippingAddressAction = { newAddress -> viewModel.updateShippingAddress(newAddress) })

    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = message, duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(true) {
        viewModel.fetchUser()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    uiState: AccountViewModel.UiState,
    scaffoldState: ScaffoldState,
    updateShippingAddressAction: (String) -> Unit
) {

    val bottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded })

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {

        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            sheetState = bottomSheetState,
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
            sheetContent = {
                UpdateShippingAddress(
                    currentAddress = uiState.user?.shippingAddress.orEmpty(),
                    updateAction = { updateShippingAddressAction(it) },
                    loading = uiState.isLoading,
                    closeAction = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    }
                )
            }) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = PAGE_HORIZONTAL_MARGIN)
            ) {

                androidx.compose.material3.Text(
                    text = "Account",
                    modifier = Modifier.padding(top = 20.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        fontSize = 25.sp
                    )
                )

                TextWithIcon(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 20.dp), textAndIconGap = 5.dp, icon = {
                        Image(
                            imageVector = Icons.Default.TextSnippet,
                            contentDescription = "",
                            modifier = Modifier
                                .size(16.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F)
                            )
                        )
                    },
                    text = {
                        Text(
                            text = "Full name",
                            modifier = Modifier.wrapContentSize(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                                fontSize = 13.sp, fontWeight = FontWeight.Normal,
                            )
                        )
                    })

                Text(
                    text = uiState.user?.fullName.orEmpty().ifEmpty { "-" },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 3.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextWithIcon(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 20.dp), textAndIconGap = 5.dp, icon = {
                        Image(
                            imageVector = Icons.Default.Email,
                            contentDescription = "",
                            modifier = Modifier
                                .size(16.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F)
                            )
                        )
                    },
                    text = {
                        Text(
                            text = "Email address",
                            modifier = Modifier.wrapContentSize(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                                fontSize = 13.sp, fontWeight = FontWeight.Normal,
                            )
                        )
                    })

                Text(
                    text = uiState.user?.emailAddress.orEmpty().ifEmpty { "-" },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 3.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextWithIcon(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 20.dp), textAndIconGap = 5.dp, icon = {
                        Image(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "",
                            modifier = Modifier
                                .size(16.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F)
                            )
                        )
                    },
                    text = {
                        Text(
                            text = "Phone number",
                            modifier = Modifier.wrapContentSize(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                                fontSize = 13.sp, fontWeight = FontWeight.Normal,
                            )
                        )
                    })

                Text(
                    text = uiState.user?.phoneNumber.orEmpty().ifEmpty { "-" },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 3.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextWithIcon(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 20.dp), textAndIconGap = 5.dp, icon = {
                        Image(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = "",
                            modifier = Modifier
                                .size(16.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F)
                            )
                        )
                    },
                    text = {
                        Text(
                            text = "Shipping address",
                            modifier = Modifier.wrapContentSize(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                                fontSize = 13.sp, fontWeight = FontWeight.Normal,
                            )
                        )
                    })

                Text(
                    text = uiState.user?.shippingAddress.orEmpty().ifEmpty { "-" },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 3.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                MinniesOutlinedDefaultButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 20.dp),
                    text = "Update shipping address",
                    enabled = uiState.isLoading.not(),
                    isLoading = uiState.isLoading
                ) {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun PreviewAccountScreen() {
    MinniesTheme {
        AccountScreen(
            uiState = AccountViewModel.UiState(),
            scaffoldState = rememberScaffoldState(),
            updateShippingAddressAction = {})
    }
}