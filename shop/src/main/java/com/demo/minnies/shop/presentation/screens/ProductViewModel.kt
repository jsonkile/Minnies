package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.demo.minnies.shop.domain.usescases.FetchProductByIdUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchProductByIdUseCase: FetchProductByIdUseCase
) :
    ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle["id"])

    val uiState = fetchProductByIdUseCase(itemId)
        .map<ViewProduct, UiState> {
            UiState.Success(it)
        }.catch { exception ->
            emit(UiState.Error(exception))
        }.onStart {
            emit(UiState.Loading)
        }

    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        class Success(val product: ViewProduct) : UiState()
        object Empty : UiState()
    }

}