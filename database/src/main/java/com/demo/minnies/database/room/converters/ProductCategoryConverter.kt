package com.demo.minnies.database.room.converters

import androidx.room.TypeConverter
import com.demo.minnies.database.models.ProductCategory

class ProductCategoryConverter {
    @TypeConverter
    fun stringToCategory(category: String) = ProductCategory.valueOf(category)

    @TypeConverter
    fun categoryToString(productCategory: ProductCategory) = productCategory.name
}