@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package com.demo.minnies.cart.presentation.screens.cart

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.shared.presentation.components.DeleteSwipeBackground
import com.demo.minnies.shared.presentation.components.ScreenInfoView
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
        gotoProductScreen = gotoProductScreen,
        deleteCartItemAction = { id -> viewModel.deleteCartItem(id) }
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
    deleteCartItemAction: (Long) -> Unit,
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
                    ScreenInfoView(
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

                        item {
                            Column {
                                androidx.compose.material3.Text(
                                    text = "Shopping cart",
                                    modifier = Modifier,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.LightGray,
                                        fontSize = 25.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                androidx.compose.material3.Text(
                                    text = "Swipe on an item to remove",
                                    modifier = Modifier,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        color = Color.LightGray,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }

                        items(items = uiState.cartItems, key = { item -> item.id }) { item ->
                            val currentItem by rememberUpdatedState(newValue = item)
                            val dismissState = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                        deleteCartItemAction(currentItem.id)
                                    }
                                    true
                                }
                            )

                            SwipeToDismiss(
                                state = dismissState,
                                modifier = Modifier
                                    .padding(vertical = 1.dp)
                                    .animateItemPlacement(),
                                background = {
                                    DeleteSwipeBackground(dismissState = dismissState)
                                },
                                directions = setOf(
                                    DismissDirection.EndToStart, DismissDirection.StartToEnd
                                ),
                                dismissThresholds = { FractionalThreshold(0.7F) }
                            ) {
                                CartItem(
                                    viewCartItem = item,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .background(
                                            shape = RoundedCornerShape(10.dp),
                                            color = MaterialTheme.colorScheme.background
                                        ),
                                    updateQuantityAction = { cartItem, step ->
                                        updateCartItemQuantityAction(cartItem, step)
                                    },
                                    imageClickAction = {
                                        gotoProductScreen(it)
                                    })
                            }
                        }

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
        ), scaffoldState = rememberScaffoldState(), { _, _ -> }, {}, {}, {})
    }
}


