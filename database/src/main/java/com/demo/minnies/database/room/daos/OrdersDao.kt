package com.demo.minnies.database.room.daos

import androidx.room.*
import com.demo.minnies.database.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(order: Order): Long

    @Query("select * from orders where id = :id")
    fun get(id: Long): Flow<Order?>

    @Query("select * from orders where user = :user")
    fun getAll(user: Long): Flow<List<Order>>

    @Update(entity = Order::class)
    suspend fun update(item: OrderIdAndStatus)
}