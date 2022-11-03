package com.demo.minnies.data.room.converters

import androidx.room.TypeConverter
import com.demo.minnies.shop.data.models.Category

class CategoryConverter {

    @TypeConverter
    fun categoryToInteger(category: Category) = Category.valueOf(category.toString())

    @TypeConverter
    fun integerToCategory(int: Int) = Category.values()[int]
}