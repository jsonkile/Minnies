package com.demo.minnies.database.room.converters

import androidx.room.TypeConverter
import com.demo.minnies.database.models.Category

class ProductCategoryConverter {
    @TypeConverter
    fun stringToCategory(category: String) = Category.valueOf(category)

    @TypeConverter
    fun categoryToString(category: Category) = category.name
}