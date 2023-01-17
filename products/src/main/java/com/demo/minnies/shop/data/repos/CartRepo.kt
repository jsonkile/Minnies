package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.models.CartItem
import com.demo.minnies.database.room.models.CartItemDetail
import com.demo.minnies.database.room.models.PartialCartItem
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    suspend fun addItem(cartItem: CartItem): Long

    suspend fun updateItem(cartItem: PartialCartItem)

    suspend fun removeItem(id: Int)

    suspend fun getItem(id: Long): CartItem?

    suspend fun getItemByProductAndUser(productId: Int, userId: Long): CartItem?

    fun getAllItems(): Flow<List<CartItem>>

    fun getCart(userId: Long): Flow<List<CartItem>>

    fun getCartWithDetails(userId: Long): Flow<List<CartItemDetail>>
}