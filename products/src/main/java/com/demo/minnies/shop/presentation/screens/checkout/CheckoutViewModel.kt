package com.demo.minnies.shop.presentation.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.auth.domain.GetUserUseCase
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPriceWithSign
import com.demo.minnies.shop.domain.usescases.FetchCartUseCase
import com.demo.minnies.shop.presentation.models.ViewCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    fetchCartUseCase: FetchCartUseCase,
    getCachedUserUseCase: GetCachedUserUseCase
) : ViewModel() {

    val uiState = combine(fetchCartUseCase(), getCachedUserUseCase()) { cart, user ->
        CartAndUser(cart, user)
    }
        .map { cartAndUser ->
            if (cartAndUser.cart.isEmpty()) {
                UiState.Empty
            } else {
                val totalCheckoutAmount =
                    cartAndUser.cart.sumOf { product -> product.quantity * product.baseProductPrice }

                val currency = cartAndUser.cart.getOrNull(0)?.currency ?: Currency.USD

                UiState.Success(
                    checkoutItems = cartAndUser.cart,
                    formattedTotalAmount = totalCheckoutAmount.toFormattedPriceWithSign(currency),
                    shippingAddress = cartAndUser.user?.shippingAddress.orEmpty()
                )
            }
        }.catch { exception ->
            emit(UiState.Error(exception))
        }.onStart {
            emit(UiState.Loading)
        }

    private val _snackBarMessage = Channel<String>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    fun checkout() {
        viewModelScope.launch {

        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        class Success(
            val checkoutItems: List<ViewCartItem>,
            val formattedTotalAmount: String,
            val shippingAddress: String
        ) :
            UiState()

        object Empty : UiState()
    }

    data class CartAndUser(val cart: List<ViewCartItem>, val user: PartialUser?)
}