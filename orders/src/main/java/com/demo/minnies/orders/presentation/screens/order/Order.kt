package com.demo.minnies.orders.presentation.screens.order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
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
import com.demo.minnies.shared.presentation.components.ErrorView
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Order(viewModel: OrderViewModel) {
    val uiState = viewModel.uiState.collectAsState(initial = OrderViewModel.UiState.Loading).value
    val scaffoldState = rememberScaffoldState()

    OrderScreen(uiState = uiState, scaffoldState = scaffoldState)

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
fun OrderScreen(uiState: OrderViewModel.UiState, scaffoldState: ScaffoldState) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Box {
            when (uiState) {

                is OrderViewModel.UiState.Error -> {
                    val message =
                        uiState.throwable.message.orEmpty().ifEmpty { "Something went wrong!" }
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
                    OrderSummary(
                        viewOrder = uiState.order,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(PAGE_HORIZONTAL_MARGIN),
                    )
                }

            }
        }
    }
}

