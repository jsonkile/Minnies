package com.demo.minnies.cart.data.repos

import com.demo.minnies.database.models.*
import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.daos.OrdersDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepoRoomImpl @Inject constructor(
    private val cartDao: CartDao,
    private val ordersDao: OrdersDao
) : CartRepo {
    override suspend fun addItem(cartItem: CartItem) = cartDao.insert(cartItem)

    override suspend fun updateItem(cartItem: CartItemIdAndQuantity) {
        cartDao.update(cartItem)
    }

    override suspend fun updateItem(cartItem: CartItemIdAndStatus) {
        cartDao.update(cartItem)
    }

    override suspend fun updateItems(cartItems: List<CartItemIdAndStatus>): Int {
        return cartDao.update(cartItems)
    }

    override suspend fun removeItem(id: Int) {
        cartDao.delete(id)
    }

    override suspend fun getItem(id: Long) = cartDao.get(id)

    override suspend fun getItemByProductAndUser(productId: Int, userId: Long) =
        cartDao.getByProductAndUser(productId, userId)

    override fun getAllItems(): Flow<List<CartItem>> = cartDao.getAll()

    override fun getCart(userId: Long): Flow<List<CartItem>> = cartDao.getCartItems(userId)

    override fun getCartWithDetails(userId: Long): Flow<List<CartItemDetail>> =
        cartDao.getCartItemsWithDetails(userId)

    override suspend fun makeOrder(order: Order) = ordersDao.insert(order)
}