package com.demo.minnies.cart.data.repos

import com.demo.minnies.database.room.models.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    fun addItem(cartItem: CartItem)

    fun updateItem(cartItem: CartItem)

    fun removeItem(id: Int)

    fun getAllItems() : Flow<List<CartItem>>

}