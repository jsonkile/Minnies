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

    override suspend fun updateQuantity(cartItem: CartItemIdAndQuantity) {
        cartDao.updateQuantity(cartItem)
    }

    override suspend fun removeItem(id: Long) {
        cartDao.delete(id)
    }

    override suspend fun getItem(id: Long) = cartDao.get(id)

    override suspend fun getItemByProductAndUser(productId: Int, userId: Long) =
        cartDao.getByProductAndUser(productId, userId)

    override fun getAllItems(): Flow<List<CartItem>> = cartDao.getAll()

    override fun getCart(userId: Long): Flow<List<CartItem>> = cartDao.getCartItems(userId)

    override fun getCartWithDetails(userId: Long): Flow<List<CartItemDetail>> =
        cartDao.getCartItemsWithDetails(userId)

    override suspend fun makeOrder(order: Order, orderItems: List<OrderItem>, userId: Long) {
        ordersDao.insertOrderAndItems(order, orderItems)
        cartDao.deleteAll(userId)
    }
}