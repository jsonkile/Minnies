package com.demo.minnies.cart.presentation.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.domain.usecases.CheckoutCartUseCase
import com.demo.minnies.cart.domain.usecases.FetchCartUseCase
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPriceWithSign
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val fetchCartUseCase: FetchCartUseCase,
    private val getCachedUserUseCase: GetCachedUserUseCase,
    private val checkoutCartUseCase: CheckoutCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _snackBarMessage = Channel<String>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    private val _checkoutCompleteEvent = Channel<Any>()
    val checkoutCompleteEvent = _checkoutCompleteEvent.receiveAsFlow()

    init {
        loadCart()

        getCachedUserUseCase().onEach { user -> _uiState.update { it.copy(shippingAddress = user?.shippingAddress.orEmpty()) } }
            .launchIn(viewModelScope)
    }

    private fun loadCart() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoadingCart = true) }
                val cart = fetchCartUseCase().first()
                val user = getCachedUserUseCase().first()

                val totalCheckoutAmount =
                    cart.sumOf { product -> product.quantity * product.baseProductPrice }

                val currency = cart.getOrNull(0)?.currency ?: Currency.USD

                _uiState.update {
                    UiState(
                        checkoutItems = cart,
                        formattedTotalAmount = totalCheckoutAmount.toFormattedPriceWithSign(currency),
                        shippingAddress = user?.shippingAddress.orEmpty()
                    )
                }
            } catch (e: Exception) {
                _snackBarMessage.trySend(e.message.orEmpty())
            } finally {
                _uiState.update { it.copy(isLoadingCart = false) }
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isCheckingOut = true) }
                checkoutCartUseCase()
                _checkoutCompleteEvent.trySend(Any())
            } catch (e: Exception) {
                _snackBarMessage.trySend(e.message.orEmpty())
            } finally {
                _uiState.update { it.copy(isCheckingOut = false) }
            }
        }
    }

    data class UiState(
        val checkoutItems: List<ViewCartItem> = emptyList(),
        val formattedTotalAmount: String = "-",
        val shippingAddress: String = "",
        val isLoadingCart: Boolean = false,
        val isCheckingOut: Boolean = false
    )
}