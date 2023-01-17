package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.ViewModel
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    fetchShopItemsByCategoriesUseCase: FetchShopItemsByCategoriesUseCase,
    fetchFeaturedShopItemsUseCase: FetchFeaturedShopItemsUseCase
) :
    ViewModel() {

    val featuredItems = fetchFeaturedShopItemsUseCase()
    val allItems = fetchShopItemsByCategoriesUseCase()
}