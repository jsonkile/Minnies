package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.shop.data.repos.CartRepo
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
        val user = checkNotNull(getCachedUserUseCase().first()){
            "Please sign in to checkout your cart."
        }

        val idsOfCartItems = cartRepo.getCart(userId = user.id).first().map { it.id }

    }
}