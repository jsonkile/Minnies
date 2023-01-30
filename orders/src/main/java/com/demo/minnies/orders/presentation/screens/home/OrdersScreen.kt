package com.demo.minnies.orders.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.orders.presentation.models.OrderContent
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.shared.presentation.components.ErrorView
import com.demo.minnies.shared.presentation.components.PageInfo
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Orders(viewModel: OrdersViewModel, gotoOrder: (String) -> Unit) {
    val uiState = viewModel.uiState.collectAsState(initial = OrdersViewModel.UiState.Loading).value
    val scaffoldState = rememberScaffoldState()

    OrdersScreen(uiState, scaffoldState, gotoOrder)

    LaunchedEffect(viewModel.uiState) {
        viewModel.snackBarMessage.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = message, duration = SnackbarDuration.Short
            )
        }
    }
}

@Composable
fun OrdersScreen(
    uiState: OrdersViewModel.UiState,
    scaffoldState: ScaffoldState,
    gotoOrder: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState, backgroundColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                is OrdersViewModel.UiState.Error -> {
                    val message =
                        uiState.throwable.message.orEmpty().ifEmpty { "Something went wrong!" }
                    ErrorView(
                        message = message,
                        icon = Icons.Default.ShoppingBag,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                OrdersViewModel.UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is OrdersViewModel.UiState.Success -> {

                    if (uiState.orders.isEmpty()) {
                        PageInfo(
                            message = "Nothing to see here.",
                            icon = Icons.Default.ShoppingBag,
                            modifier = Modifier
                                .align(Alignment.Center),
                            contentDescription = "empty orders"
                        )
                    } else {
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
                                Text(
                                    text = "Orders",
                                    modifier = Modifier,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.LightGray,
                                        fontSize = 25.sp
                                    )
                                )
                            }

                            items(uiState.orders) { item ->
                                OrderItemComposable(
                                    item,
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .clickable {
                                            gotoOrder(item.ref)
                                        }
                                )
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
fun PreviewMyOrdersScreen() {
    MinniesTheme {
        OrdersScreen(
            scaffoldState = rememberScaffoldState(),
            uiState = OrdersViewModel.UiState.Success(
                orders = listOf(
                    ViewOrder(
                        createdTime = "23/23/23",
                        id = 1,
                        progress = 40,
                        status = OrderStatus.Ongoing,
                        items = listOf(
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            ),
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            ),
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            )
                        ),
                        ref = "456YTRF",
                        totalAmount = "$5.00"
                    ),
                    ViewOrder(
                        createdTime = "23/23/23",
                        id = 1,
                        progress = 40,
                        status = OrderStatus.Ongoing,
                        items = listOf(
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            ),
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            ),
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            )
                        ),
                        ref = "456YTRF",
                        totalAmount = "$5.00"
                    ),
                    ViewOrder(
                        createdTime = "23/23/23",
                        id = 1,
                        progress = 40,
                        status = OrderStatus.Ongoing,
                        items = listOf(
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            ),
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            )
                        ),
                        ref = "456YTRF",
                        totalAmount = "$5.00"
                    ),
                    ViewOrder(
                        createdTime = "23/23/23",
                        id = 1,
                        progress = 40,
                        status = OrderStatus.Ongoing,
                        items = listOf(
                            OrderContent(
                                amount = "",
                                productImage = "",
                                productName = "",
                                productId = 1,
                                quantity = 1
                            )
                        ),
                        ref = "456YTRF",
                        totalAmount = "$5.00"
                    )
                )
            ), gotoOrder = {})
    }
}