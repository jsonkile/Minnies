package com.demo.minnies.database.room.converters

import androidx.room.TypeConverter
import com.demo.minnies.database.room.models.Category

class CategoryConverter {

    @TypeConverter
    fun categoryToInteger(category: Category) = Category.valueOf(category.toString())

    @TypeConverter
    fun integerToCategory(int: Int) = Category.values()[int]
}