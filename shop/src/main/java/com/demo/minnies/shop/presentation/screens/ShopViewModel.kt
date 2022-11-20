package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.ViewModel
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoryUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ShopScreenViewModel @Inject constructor(
    fetchShopItemsByCategoriesUseCase: FetchShopItemsByCategoriesUseCase,
    fetchFeaturedShopItemsUseCase: FetchFeaturedShopItemsUseCase
) :
    ViewModel() {

    val featuredItems = fetchFeaturedShopItemsUseCase()
    val allItems = fetchShopItemsByCategoriesUseCase()
}