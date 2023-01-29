package com.demo.minnies.cart.domain.usecases

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.data.repos.CartRepo
import com.demo.minnies.database.models.Order
import com.demo.minnies.database.models.OrderItem
import com.demo.minnies.shared.utils.SIGN_IN_MESSAGE
import com.demo.minnies.shared.utils.getRandomString
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface CheckoutCartUseCase {
    suspend operator fun invoke()
}

class CheckoutCartUseCaseImpl @Inject constructor(
    private val cartRepo: CartRepo,
    private val getCachedUserUseCase: GetCachedUserUseCase
) :
    CheckoutCartUseCase {
    override suspend fun invoke() {
        val user = checkNotNull(getCachedUserUseCase().first()) { SIGN_IN_MESSAGE }

        val ref = getRandomString(7)

        val cart = cartRepo.getCartWithDetails(user.id).first()

        val orderItems = cart.map { cartItem ->
            OrderItem(
                orderRef = ref,
                productId = cartItem.productId.toLong(),
                checkoutPrice = cartItem.productPrice,
                quantity = cartItem.quantity
            )
        }

        cartRepo.makeOrder(order = Order(ref = ref, user = user.id), orderItems = orderItems, user.id)
    }

}