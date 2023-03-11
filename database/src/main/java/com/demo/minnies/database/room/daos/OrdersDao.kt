package com.demo.minnies.database.room.daos

import androidx.room.*
import com.demo.minnies.database.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)

    @Transaction
    suspend fun insertOrderAndItems(order: Order, items: List<OrderItem>) {
        insert(order)
        insertOrderItems(items)
    }

    @Query("select * from orders where ref = :ref")
    fun get(ref: String): Flow<OrderWithItems?>

    @Query("select * from orders where user = :user")
    fun getAll(user: Long): Flow<List<Order>>

    @Update(entity = Order::class)
    suspend fun update(item: OrderIdAndStatus)

    @Transaction
    @Query("select * from orders where user = :user order by created_at desc")
    fun getUserOrders(user: Long): Flow<List<OrderWithItems>>
}