package com.demo.minnies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "cart_items")
    val cartItems: List<Long>,
    @ColumnInfo(defaultValue = "Ongoing")
    val status: OrderStatus = OrderStatus.Ongoing,
    @ColumnInfo(name = "created_at")
    val createdTime: Long = System.currentTimeMillis(),
    val user: Long,
)

enum class OrderStatus {
    Ongoing, Completed, Cancelled
}

data class OrderIdAndStatus(val id: Long, val status: OrderStatus)


