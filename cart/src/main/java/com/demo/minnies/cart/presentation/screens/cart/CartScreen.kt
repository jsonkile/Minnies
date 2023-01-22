package com.demo.minnies.cart.presentation.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.shared.presentation.components.ErrorView
import com.demo.minnies.shared.presentation.components.MinniesDefaultButton
import com.demo.minnies.shared.presentation.components.PageInfo
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.Currency
import kotlinx.coroutines.flow.collectLatest

const val ERROR_AREA_TEST_TAG = "ERROR_AREA_TEST_TAG"
const val LOADING_TEST_TAG = "LOADING_TEST_TAG"

@Composable
fun Cart(
    viewModel: CartViewModel,
    gotoCheckoutScreen: () -> Unit,
    gotoProductScreen: (Long) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState(initial = CartViewModel.UiState.Empty).value
    val scaffoldState = rememberScaffoldState()

    CartScreen(
        uiState = uiState,
        scaffoldState = scaffoldState,
        updateCartItemQuantityAction = { cartItem, step ->
            viewModel.updateCartItemQuantity(cartItem, step)
        },
        gotoCheckoutScreen = gotoCheckoutScreen,
        gotoProductScreen = gotoProductScreen
    )

    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = message, duration = SnackbarDuration.Short
            )
        }
    }
}

@Composable
fun CartScreen(
    uiState: CartViewModel.UiState,
    scaffoldState: ScaffoldState,
    updateCartItemQuantityAction: (ViewCartItem, Int) -> Unit,
    gotoCheckoutScreen: () -> Unit,
    gotoProductScreen: (Long) -> Unit
) {

    val lazyListState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState, backgroundColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                CartViewModel.UiState.Empty -> {
                    PageInfo(
                        message = "Your cart is empty.",
                        icon = Icons.Default.ShoppingCart,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(ERROR_AREA_TEST_TAG),
                        contentDescription = "empty cart"
                    )
                }

                is CartViewModel.UiState.Error -> {
                    val message =
                        uiState.throwable.message.orEmpty().ifEmpty { "Something went wrong!" }
                    ErrorView(
                        message = message,
                        icon = Icons.Default.ShoppingCart,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(ERROR_AREA_TEST_TAG)
                    )
                }

                CartViewModel.UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(LOADING_TEST_TAG)
                    )
                }

                is CartViewModel.UiState.Success -> {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(PAGE_HORIZONTAL_MARGIN),
                        horizontalAlignment = Alignment.Start,
                        state = lazyListState,
                        contentPadding = PaddingValues(
                            horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 40.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(uiState.cartItems) { item ->
                            CartItem(
                                viewCartItem = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                updateQuantityAction = { item, step ->
                                    updateCartItemQuantityAction(
                                        item,
                                        step
                                    )
                                },
                                imageClickAction = {
                                    gotoProductScreen(it)
                                })
                        }

                        if (uiState.cartItems.isNotEmpty()) {
                            item {
                                MinniesDefaultButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    text = "Checkout",
                                    icon = Icons.Default.ShoppingCartCheckout
                                ) {
                                    gotoCheckoutScreen()
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCartScreen() {
    MinniesTheme {
        CartScreen(uiState = CartViewModel.UiState.Success(
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
                    currency = Currency.EUR
                )
            )
        ), scaffoldState = rememberScaffoldState(), { _, _ -> }, {}, {})
    }
}


