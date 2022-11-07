package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.ViewModel
import com.demo.minnies.shop.domain.usescases.FetchFeaturedShopItemsUseCase
import com.demo.minnies.shop.domain.usescases.FetchShopItemsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopScreenViewModel @Inject constructor(
    fetchShopItemsByCategoriesUseCase: FetchShopItemsByCategoriesUseCase,
    fetchFeaturedShopItemsUseCase: FetchFeaturedShopItemsUseCase
) :
    ViewModel() {

    val shopItemsByCategories = fetchShopItemsByCategoriesUseCase()
    val featuredItems = fetchFeaturedShopItemsUseCase()

}