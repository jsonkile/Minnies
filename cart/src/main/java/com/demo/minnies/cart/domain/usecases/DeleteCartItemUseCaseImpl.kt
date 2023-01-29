package com.demo.minnies.cart.domain.usecases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.database.models.CartItemIdAndQuantity
import com.demo.minnies.shared.utils.SIGN_IN_MESSAGE
import kotlinx.coroutines.flow.first
import javax.inject.Inject


interface DeleteCartItemUseCase {
    suspend operator fun invoke(id: Long)
}

class DeleteCartItemUseCaseImpl @Inject constructor(
    private val cartRepo: CartRepo,
    private val getCachedUserUseCase: GetCachedUserUseCase
) : DeleteCartItemUseCase {
    override suspend operator fun invoke(id: Long) {
        check(getCachedUserUseCase().first() != null) { SIGN_IN_MESSAGE }

        cartRepo.removeItem(id)
    }
}