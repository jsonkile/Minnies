package com.demo.minnies.database.room.daos

import androidx.room.*
import com.demo.minnies.database.models.CartItem
import com.demo.minnies.database.models.CartItemDetail
import com.demo.minnies.database.models.CartItemIdAndQuantity
import com.demo.minnies.database.models.CartItemIdAndStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CartItem): Long

    @Query("select * from cart_items where id = :id")
    suspend fun get(id: Long): CartItem?

    @Query("select * from cart_items where userId = :userId and productId = :productId")
    suspend fun getByProductAndUser(productId: Int, userId: Long): CartItem?

    @Query("select * from cart_items")
    fun getAll(): Flow<List<CartItem>>

    @Query("select * from cart_items where userId = :userId")
    fun getCartItems(userId: Long): Flow<List<CartItem>>

    @Query("select * from CartItemDetail where userId = :userId")
    fun getCartItemsWithDetails(userId: Long): Flow<List<CartItemDetail>>

    @Update(entity = CartItem::class)
    suspend fun update(item: CartItemIdAndQuantity)

    @Update(entity = CartItem::class)
    suspend fun update(item: CartItemIdAndStatus)

    @Update(entity = CartItem::class)
    suspend fun update(items: List<CartItemIdAndStatus>): Int

    @Query("delete from cart_items where id = :id")
    suspend fun delete(id: Int)
}