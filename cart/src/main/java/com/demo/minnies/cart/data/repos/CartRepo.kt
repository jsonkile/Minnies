package com.demo.minnies.cart.data.repos

import com.demo.minnies.database.models.*
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    suspend fun addItem(cartItem: CartItem): Long

    suspend fun updateQuantity(cartItem: CartItemIdAndQuantity)

    suspend fun removeItem(id: Long)

    suspend fun getItem(id: Long): CartItem?

    suspend fun getItemByProductAndUser(productId: Int, userId: Long): CartItem?

    fun getAllItems(): Flow<List<CartItem>>

    fun getCart(userId: Long): Flow<List<CartItem>>

    fun getCartWithDetails(userId: Long): Flow<List<CartItemDetail>>

    suspend fun makeOrder(order: Order, orderItems: List<OrderItem>, userId: Long)
}