package com.demo.minnies.cart.data.repos

import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.models.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepoRoomImpl @Inject constructor(private val cartDao: CartDao) : CartRepo {
    override fun addItem(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    override fun updateItem(cartItem: CartItem) {
        cartDao.update(cartItem)
    }

    override fun removeItem(id: Int) {
        cartDao.delete(id)
    }

    override fun getAllItems(): Flow<List<CartItem>> = cartDao.getAll()
}