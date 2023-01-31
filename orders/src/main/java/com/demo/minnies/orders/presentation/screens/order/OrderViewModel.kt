package com.demo.minnies.orders.presentation.screens.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.orders.domain.CancelOrderUseCase
import com.demo.minnies.orders.domain.ConfirmOrderUseCase
import com.demo.minnies.orders.domain.FetchOrderUseCase
import com.demo.minnies.orders.presentation.models.ViewOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchOrderUseCase: FetchOrderUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val confirmOrderUseCase: ConfirmOrderUseCase
) : ViewModel() {

    val itemId: String = checkNotNull(savedStateHandle["ref"])

    val uiState = fetchOrderUseCase(itemId)
        .map<ViewOrder, UiState> {
            UiState.Success(it)
        }.catch { exception ->
            emit(UiState.Error(exception))
        }.onStart {
            emit(UiState.Loading)
        }.shareIn(viewModelScope, SharingStarted.Lazily)

    private val _snackBarMessage = Channel<String>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    fun cancelOrder() {
        viewModelScope.launch {
            try {
                cancelOrderUseCase(itemId)
                _snackBarMessage.trySend("Your order has been cancelled.")
            } catch (e: Exception) {
                _snackBarMessage.trySend(e.message.orEmpty())
            }
        }
    }

    fun confirmOrder() {
        viewModelScope.launch {
            try {
                confirmOrderUseCase(itemId)
                _snackBarMessage.trySend("Your order has been confirmed.")
            } catch (e: Exception) {
                _snackBarMessage.trySend(e.message.orEmpty())
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        class Success(
            val order: ViewOrder
        ) : UiState()
    }

}