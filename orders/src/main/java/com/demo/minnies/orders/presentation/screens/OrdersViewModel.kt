package com.demo.minnies.orders.presentation.screens

import androidx.lifecycle.ViewModel
import com.demo.minnies.orders.domain.MakeOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val makeOrderUseCase: MakeOrderUseCase) :
    ViewModel() {



}