package com.demo.minnies.database.models

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userId: Long,
    var productId: Int,
    var quantity: Int
)

@DatabaseView("select cart_items.id, cart_items.quantity, cart_items.userId, products.id as productId, products.name as productName, products.image as productImage, products.price as productPrice from cart_items inner join products on cart_items.productId = products.id")
data class CartItemDetail(
    val id: Long,
    val userId: Long,
    val quantity: Int,
    val productId: Int,
    val productName: String,
    val productImage: String,
    val productPrice: Double,
)

data class CartItemIdAndQuantity(val id: Long, val quantity: Int)