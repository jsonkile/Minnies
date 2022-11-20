package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    val itemId: Int = checkNotNull(savedStateHandle["id"])

}