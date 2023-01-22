package com.demo.minnies.cart.domain.usecases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.data.repos.CartRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface CheckoutCartUseCase {
    suspend operator fun invoke()
}

class CheckoutCartUseCaseImpl @Inject constructor(
    val cartRepo: CartRepo,
    val getCachedUserUseCase: GetCachedUserUseCase
) : CheckoutCartUseCase {
    override suspend fun invoke() {
        val user = checkNotNull(getCachedUserUseCase().first()) {
            "Please sign in to complete this action."
        }

        val idsOfCartItems = cartRepo.getCart(userId = user.id).first().map { it.id }

    }
}