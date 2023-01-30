package com.demo.minnies.cart.presentation.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.demo.minnies.auth.util.BioAuth
import com.demo.minnies.cart.presentation.screens.cart.LOADING_TEST_TAG
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.shared.presentation.components.MinniesDefaultButton
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.Currency
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest


@Composable
fun Checkout(
    viewModel: CheckoutViewModel,
    gotoAccountScreen: () -> Unit,
    gotoOrdersScreen: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current as FragmentActivity
    val bioAuth = BioAuth(activity = context, successAction = {
        viewModel.checkout()
    })

    CheckoutScreen(uiState, scaffoldState, gotoAccountScreen) {
        if (bioAuth.canAuthenticate()) {
            bioAuth.authenticate(
                dialogTitle = "Complete your order",
                dialogSubtitle = "Log in using your biometric credential",
                dialogCancelButtonTitle = "Cancel"
            )
        } else {
            viewModel.checkout()
        }
    }

    LaunchedEffect(viewModel.snackBarMessage) {
        viewModel.snackBarMessage.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = message, duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(viewModel.checkoutCompleteEvent) {
        viewModel.checkoutCompleteEvent.collectLatest {
            gotoOrdersScreen()
        }
    }
}


@Composable
fun CheckoutScreen(
    uiState: CheckoutViewModel.UiState,
    scaffoldState: ScaffoldState,
    gotoAccountScreen: () -> Unit,
    checkout: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState, backgroundColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Box(modifier = Modifier.fillMaxSize()) {

            if (uiState.isLoadingCart) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(LOADING_TEST_TAG)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PAGE_HORIZONTAL_MARGIN),
                    horizontalAlignment = Alignment.Start,
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 0.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    item {
                        Text(
                            text = "Checkout",
                            modifier = Modifier,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                fontSize = 25.sp
                            )
                        )
                    }

                    items(uiState.checkoutItems) { item ->
                        CheckoutItem(
                            viewCartItem = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    }

                    item {
                        Divider(
                            modifier = Modifier,
                            thickness = .5.dp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    item {
                        CheckoutSummary(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            totalCartCount = uiState.checkoutItems.size,
                            totalCheckoutAmount = uiState.formattedTotalAmount,
                            shippingAddress = uiState.shippingAddress
                        ) {
                            gotoAccountScreen()
                        }
                    }

                    item {
                        MinniesDefaultButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 10.dp),
                            text = "Place order",
                            isLoading = uiState.isCheckingOut
                        ) {
                            checkout()
                        }
                    }

                }
            }

        }
    }
}


@Preview
@Composable
fun PreviewCheckoutScreen() {
    MinniesTheme {
        CheckoutScreen(
            uiState = CheckoutViewModel.UiState(
                listOf(
                    ViewCartItem(
                        id = 1,
                        quantity = 1,
                        productId = 1,
                        productName = "Cap",
                        baseProductPrice = 3.9,
                        productImage = "",
                        formattedProductPrice = "N3,000",
                        formattedTotalAmount = "N3,000",
                        convertedProductPrice = 5.0,
                        currency = Currency.USD
                    ),

                    ViewCartItem(
                        id = 2,
                        quantity = 2,
                        productId = 1,
                        productName = "Trouser",
                        baseProductPrice = 4.0,
                        productImage = "",
                        formattedProductPrice = "N2,000",
                        formattedTotalAmount = "N4,000",
                        convertedProductPrice = 5.0,
                        currency = Currency.USD
                    )
                ), formattedTotalAmount = "N3,000", shippingAddress = "The Ireland"
            ), scaffoldState = rememberScaffoldState(), gotoAccountScreen = {}, {})
    }
}


@Preview
@Composable
fun PreviewCheckoutScreen2() {
    MinniesTheme {
        CheckoutScreen(
            uiState = CheckoutViewModel.UiState(
                listOf(
                    ViewCartItem(
                        id = 1,
                        quantity = 1,
                        productId = 1,
                        productName = "Cap",
                        baseProductPrice = 3.9,
                        productImage = "",
                        formattedProductPrice = "N3,000",
                        formattedTotalAmount = "N3,000",
                        convertedProductPrice = 5.0,
                        currency = Currency.USD
                    ),

                    ViewCartItem(
                        id = 2,
                        quantity = 2,
                        productId = 1,
                        productName = "Trouser",
                        baseProductPrice = 4.0,
                        productImage = "",
                        formattedProductPrice = "N2,000",
                        formattedTotalAmount = "N4,000",
                        convertedProductPrice = 5.0,
                        currency = Currency.USD
                    )
                ),
                formattedTotalAmount = "N3,000",
                shippingAddress = "The Ireland",
                isCheckingOut = true
            ), scaffoldState = rememberScaffoldState(), gotoAccountScreen = {}, {})
    }
}