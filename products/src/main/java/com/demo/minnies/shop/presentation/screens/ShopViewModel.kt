package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.ViewModel
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    fetchProductsByCategoriesUseCase: FetchProductsByCategoriesUseCase,
    fetchFeaturedShopItemsUseCase: FetchFeaturedShopItemsUseCase
) :
    ViewModel() {

    val featuredItems = fetchFeaturedShopItemsUseCase()
    val allItems = fetchProductsByCategoriesUseCase()
}