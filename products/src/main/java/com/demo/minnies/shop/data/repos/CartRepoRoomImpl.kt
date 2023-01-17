package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.models.CartItem
import com.demo.minnies.database.room.models.CartItemDetail
import com.demo.minnies.database.room.models.PartialCartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepoRoomImpl @Inject constructor(private val cartDao: CartDao) : CartRepo {
    override suspend fun addItem(cartItem: CartItem) = cartDao.insert(cartItem)

    override suspend fun updateItem(cartItem: PartialCartItem) {
        cartDao.update(cartItem)
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
}