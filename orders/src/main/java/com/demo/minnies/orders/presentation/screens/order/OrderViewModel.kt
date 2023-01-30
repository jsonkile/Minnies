package com.demo.minnies.orders.presentation.screens.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.orders.domain.FetchOrderUseCase
import com.demo.minnies.orders.presentation.models.ViewOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchOrderUseCase: FetchOrderUseCase
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

    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        class Success(
            val order: ViewOrder
        ) : UiState()
    }

}