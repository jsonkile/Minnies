package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.minnies.database.models.ProductCategory
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchProductsByCategoriesUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    fetchProductsByCategoriesUseCase: FetchProductsByCategoriesUseCase,
    fetchFeaturedShopItemsUseCase: FetchFeaturedShopItemsUseCase
) :
    ViewModel() {

    private val featuredItems = fetchFeaturedShopItemsUseCase()
    private val allItems = fetchProductsByCategoriesUseCase()

    val uiState = combine(allItems, featuredItems) { all, featured ->
        UiState.Success(featured = featured, all = all)
    }
        .map<UiState.Success, UiState> { UiState.Success(it.featured, it.all) }
        .onStart { emit(UiState.Loading) }
        .catch { emit(UiState.Error(Throwable(it.message.orEmpty()))) }
        .stateIn(viewModelScope, SharingStarted.Lazily, UiState.Loading)


    sealed class UiState {
        object Loading : UiState()
        class Error(val throwable: Throwable) : UiState()
        class Success(
            val featured: List<ViewProduct> = emptyList(),
            val all: Map<ProductCategory, List<ViewProduct>> = emptyMap()
        ) : UiState()
    }
}