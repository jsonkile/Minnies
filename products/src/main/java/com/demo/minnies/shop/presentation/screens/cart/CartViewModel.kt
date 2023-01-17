package com.demo.minnies.shop.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.database.room.models.PartialCartItem
import com.demo.minnies.shop.domain.usescases.FetchCartUseCase
import com.demo.minnies.shop.domain.usescases.UpdateCartItemUseCase
import com.demo.minnies.shop.presentation.models.ViewCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    fetchCartUseCase: FetchCartUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase
) :
    ViewModel() {

    val uiState = fetchCartUseCase()
        .map {
            if (it.isEmpty()) UiState.Empty else UiState.Success(it)
        }.catch { exception ->
            emit(UiState.Error(exception))
        }.onStart {
            emit(UiState.Loading)
        }

    private val _snackBarMessage = Channel<String>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    fun updateCartItemQuantity(viewCartItem: ViewCartItem, step: Int) {
        viewModelScope.launch {
            try {
                updateCartItemUseCase(
                    PartialCartItem(
                        id = viewCartItem.id, viewCartItem.quantity + step
                    )
                )

                _snackBarMessage.trySend("Your cart was updated.")
            } catch (e: Exception) {
                _snackBarMessage.trySend(e.message.orEmpty())
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        class Success(val cartItems: List<ViewCartItem>) : UiState()
        object Empty : UiState()
    }
}