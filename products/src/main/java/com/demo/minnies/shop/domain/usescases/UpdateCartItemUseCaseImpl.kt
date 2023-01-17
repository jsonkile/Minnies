package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.auth.domain.GetCachedUserUseCaseImpl
import com.demo.minnies.database.room.models.PartialCartItem
import com.demo.minnies.shop.data.repos.CartRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject


interface UpdateCartItemUseCase {
    suspend operator fun invoke(cartItem: PartialCartItem)
}

class UpdateCartItemUseCaseImpl @Inject constructor(
    private val cartRepo: CartRepo,
    private val getCachedUserUseCaseImpl: GetCachedUserUseCaseImpl
) : UpdateCartItemUseCase {
    override suspend operator fun invoke(cartItem: PartialCartItem) {
        check(getCachedUserUseCaseImpl().first() != null) {
            "Please log in to update your cart."
        }

        check(cartItem.quantity != 0) {
            "Well, that's not possible."
        }

        cartRepo.updateItem(cartItem)
    }
}