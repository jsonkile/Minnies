package com.demo.minnies.orders.presentation.screens.order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.demo.minnies.shared.presentation.components.MinniesDefaultButton
import com.demo.minnies.shared.presentation.components.MinniesOutlinedDefaultButton
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.GENERIC_ERROR_MESSAGE
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Order(viewModel: OrderViewModel) {
    val uiState = viewModel.uiState.collectAsState(initial = OrderViewModel.UiState.Loading).value
    val scaffoldState = rememberScaffoldState()

    OrderScreen(
        uiState = uiState,
        scaffoldState = scaffoldState,
        cancelOrderAction = {
            viewModel.cancelOrder()
        },
        completeOrderAction = {
            viewModel.confirmOrder()
        })

    LaunchedEffect(uiState) {
        if (uiState is OrderViewModel.UiState.Error) {
            val message = uiState.throwable.message.orEmpty().ifEmpty { "Something went wrong!" }
            scaffoldState.snackbarHostState.showSnackbar(
                message,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = message, duration = SnackbarDuration.Long
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    uiState: OrderViewModel.UiState,
    scaffoldState: ScaffoldState,
    cancelOrderAction: () -> Unit,
    completeOrderAction: () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Box(Modifier.fillMaxSize()) {
            when (uiState) {

                is OrderViewModel.UiState.Error -> {
                    val message =
                        uiState.throwable.message.orEmpty().ifEmpty { GENERIC_ERROR_MESSAGE }
                    ErrorView(
                        message = message,
                        icon = Icons.Default.HeartBroken,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                OrderViewModel.UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                is OrderViewModel.UiState.Success -> {

                    val lazyListState = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(PAGE_HORIZONTAL_MARGIN),
                        horizontalAlignment = Alignment.Start,
                        state = lazyListState,
                        contentPadding = PaddingValues(
                            horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 10.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {

                        item {

                            androidx.compose.material3.Text(
                                text = "Order Nº${uiState.order.ref}",
                                modifier = Modifier.padding(top = 0.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray,
                                    fontSize = 25.sp
                                )
                            )

                        }

                        item {
                            OrderSummary(
                                viewOrder = uiState.order,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                            )
                        }


                        items(uiState.order.items) { item ->
                            OrderContentComposable(
                                orderContent = item, modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            )
                        }


                        if (uiState.order.status == OrderStatus.Ongoing) {
                            item {
                                MinniesDefaultButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(top = 10.dp),
                                    text = "Confirm",
                                    isLoading = false
                                ) { completeOrderAction() }
                            }


                            item {
                                MinniesOutlinedDefaultButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    text = "Cancel",
                                    isLoading = false
                                ) { cancelOrderAction() }
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
fun PreviewOrderScreen() {
    MinniesTheme {
        OrderScreen(
            uiState = OrderViewModel.UiState.Success(
                order = ViewOrder(
                    ref = "",
                    totalAmount = "N56,000",
                    id = 0L,
                    status = OrderStatus.Ongoing,
                    progress = 10,
                    createdTime = "23-12-2013",
                    items = listOf(
                        OrderContent(
                            productId = 1L,
                            productName = "Joseph",
                            amount = "N3,000",
                            quantity = 2,
                            productImage = ""
                        ),
                        OrderContent(
                            productId = 1L,
                            productName = "Joseph",
                            amount = "N3,000",
                            quantity = 2,
                            productImage = ""
                        )
                    )
                )
            ), scaffoldState = rememberScaffoldState(), {}, {}
        )
    }
}

