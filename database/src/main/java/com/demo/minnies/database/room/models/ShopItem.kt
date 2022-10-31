package com.demo.minnies.database.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItem(
    @PrimaryKey var id: Int,
    var name: String,
    var image: String,
    var description: String,
    var sizes: List<Int>,
    var category: Category
)

enum class Category {
    Accessory, Shorts, Top, Kicks
}