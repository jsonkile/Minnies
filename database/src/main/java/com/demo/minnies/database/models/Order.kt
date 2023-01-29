package com.demo.minnies.database.models

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "orders")
data class Order(
    val id: Long = 0,
    @ColumnInfo(defaultValue = "Ongoing")
    val status: OrderStatus = OrderStatus.Ongoing,
    @ColumnInfo(name = "created_at")
    val createdTime: Long = System.currentTimeMillis(),
    val user: Long,
    @PrimaryKey(autoGenerate = false)
    val ref: String
)

enum class OrderStatus {
    Ongoing, Completed, Cancelled
}

@Entity(tableName = "order_items")
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderRef: String,
    val productId: Long,
    val checkoutPrice: Double = 0.0,
    val quantity: Int
)

@DatabaseView("select order_items.orderRef as orderRef, order_items.id, order_items.checkoutPrice, order_items.productId, order_items.quantity, products.name as productName, products.image as productImage from order_items inner join products on order_items.productId = products.id")
data class OrderItemDetail(
    val id: Long,
    val orderRef: String,
    val checkoutPrice: Double,
    val quantity: Int,
    val productId: Long,
    val productName: String,
    val productImage: String
)


data class OrderWithItems(
    @Embedded val order: Order,
    @Relation(parentColumn = "ref", entityColumn = "orderRef")
    val items: List<OrderItemDetail>
)

data class OrderIdAndStatus(val ref: String, val status: OrderStatus)


