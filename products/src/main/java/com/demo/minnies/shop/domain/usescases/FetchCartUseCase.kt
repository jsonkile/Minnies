package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.shop.presentation.models.ViewCartItem
import kotlinx.coroutines.flow.Flow

interface FetchCartUseCase {
    operator fun invoke(): Flow<List<ViewCartItem>>
}