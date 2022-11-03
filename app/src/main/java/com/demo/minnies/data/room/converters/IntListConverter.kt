package com.demo.minnies.data.room.converters

import androidx.room.TypeConverter

class IntListConverter {

    @TypeConverter
    fun listOfIntToString(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToListOfInt(string: String): List<Int> {
        return if (string.isEmpty()) emptyList() else string.split(",").map { it.toInt() }
    }
}