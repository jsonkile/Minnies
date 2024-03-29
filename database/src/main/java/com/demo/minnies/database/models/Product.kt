package com.demo.minnies.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var image: String,
    var description: String,
    var sizes: List<Int> = emptyList(),
    var productCategory: ProductCategory,
    var price: Double,
    var featured: Boolean = false,
    var rating: Double = 0.0
)

enum class ProductCategory {
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