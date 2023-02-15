package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.shop.domain.usescases.SearchProductsUseCase
import com.demo.minnies.shop.domain.usescases.SearchProductsUseCaseImpl
import com.demo.minnies.shop.presentation.models.ViewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(private val searchProductsUseCase: SearchProductsUseCase) :
    ViewModel() {

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    val uiState =
        searchTerm.filterNot { it.length < 2 }
            .flatMapLatest { term ->
                UiState.Loading
                searchProductsUseCase(term.trim())
            }
            .map { results ->
                if (results.isEmpty()) UiState.NoResults else UiState.SearchResults(results)
            }
            .catch { exception ->
                emit(UiState.Error(exception))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UiState.Default,
            )

    fun updateSearchTerm(term: String) {
        _searchTerm.value = term
    }

    sealed class UiState {

        object NoResults : UiState()
        object Default : UiState()
        class SearchResults(val results: List<ViewProduct>) : UiState()
        class Error(val throwable: Throwable) : UiState()
        object Loading : UiState()

    }
}