package com.demo.minnies.shop.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var image: String,
    var description: String,
    var sizes: List<Int>,
    var category: Category,
    var price: Double
)

enum class Category {
    Accessory {
        override val publicName: String = "Accessories"
    },
    Shorts {
        override val publicName: String = "Shorts and Bottoms"
    },
    Top {
        override val publicName = "Tees and Tanks"
    },
    Kicks {
        override val publicName: String = "Boots, Kicks and Trainers"
    };

    abstract val publicName: String
}