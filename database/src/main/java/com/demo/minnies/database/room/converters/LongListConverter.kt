package com.demo.minnies.database.room.converters

import androidx.room.TypeConverter

class LongListConverter {

    @TypeConverter
    fun listOfLongToString(list: List<Long>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToListOfLong(string: String): List<Long> {
        return if (string.isEmpty()) emptyList() else string.split(",").map { it.toLong() }
    }
}