package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.ViewModel
import com.demo.minnies.shop.domain.usescases.FetchAndGroupShopItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopScreenViewModel @Inject constructor(fetchAndGroupShopItemsUseCase: FetchAndGroupShopItemsUseCase) :
    ViewModel() {

    val shopItems = fetchAndGroupShopItemsUseCase(mock = true)

}