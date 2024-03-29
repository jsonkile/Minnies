package com.demo.minnies.cart.domain.usecases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.database.models.CartItemIdAndQuantity
import com.demo.minnies.shared.utils.SIGN_IN_MESSAGE
import kotlinx.coroutines.flow.first
import javax.inject.Inject


interface UpdateCartItemUseCase {
    suspend operator fun invoke(cartItem: CartItemIdAndQuantity)
}

class UpdateCartItemUseCaseImpl @Inject constructor(
    private val cartRepo: CartRepo,
    private val getCachedUserUseCase: GetCachedUserUseCase
) : UpdateCartItemUseCase {
    override suspend operator fun invoke(cartItem: CartItemIdAndQuantity) {
        check(getCachedUserUseCase().first() != null) { SIGN_IN_MESSAGE }

        check(cartItem.quantity != 0) {
            "Well, that's not possible."
        }

        cartRepo.updateQuantity(cartItem)
    }
}