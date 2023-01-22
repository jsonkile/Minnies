package com.demo.minnies.cart.domain.usecases

import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import kotlinx.coroutines.flow.Flow

interface FetchCartUseCase {
    operator fun invoke(): Flow<List<ViewCartItem>>
}