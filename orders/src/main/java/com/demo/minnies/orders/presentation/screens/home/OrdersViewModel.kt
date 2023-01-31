package com.demo.minnies.orders.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.orders.domain.FetchUserOrdersUseCase
import com.demo.minnies.orders.presentation.models.ViewOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(fetchUserOrdersUseCase: FetchUserOrdersUseCase) :
    ViewModel() {

    private val _snackBarMessage = Channel<String>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    val uiState = fetchUserOrdersUseCase()
        .map<List<ViewOrder>, UiState> {
            UiState.Success(it)
        }.catch { e ->
            emit(UiState.Error(e))
        }.onStart { emit(UiState.Loading) }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L))


    sealed class UiState {
        class Success(val orders: List<ViewOrder> = emptyList()) : UiState()
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
    }


}