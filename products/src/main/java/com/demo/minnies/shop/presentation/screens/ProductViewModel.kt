package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.domain.usescases.AddToCartUseCase
import com.demo.minnies.shop.domain.usescases.FetchProductByIdUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.util.ADDED_T0_CART_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchProductByIdUseCase: FetchProductByIdUseCase,
    getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase,
    getCachedUserUseCase: GetCachedUserUseCase,
    private val addToCartUseCase: AddToCartUseCase
) :
    ViewModel() {

    val itemId: Int = checkNotNull(savedStateHandle["id"])

    val uiState = combine(
        fetchProductByIdUseCase(itemId),
        getUserCurrencyPreferenceUseCase(),
        getCachedUserUseCase()
    ) { product, currencyPreference, loggedInUser ->
        Triple(product, currencyPreference, loggedInUser)
    }.map<Triple<ViewProduct, String, PartialUser?>, UiState> {
        UiState.Success(it.first, Currency.valueOf(it.second), it.third)
    }.catch { exception ->
        emit(UiState.Error(exception))
    }.onStart {
        emit(UiState.Loading)
    }

    private val _snackBarMessage = Channel<String>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    fun addToCart(productId: Int, quantity: Int, userId: Long?) {
        viewModelScope.launch {
            try {
                addToCartUseCase(productId, quantity, userId)
                _snackBarMessage.send(ADDED_T0_CART_MESSAGE)
            } catch (e: Exception) {
                _snackBarMessage.send(e.message.orEmpty())
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        class Success(
            val product: ViewProduct,
            val currency: Currency = Currency.USD,
            val loggedInUser: PartialUser? = null
        ) : UiState()

        object Empty : UiState()
    }
}