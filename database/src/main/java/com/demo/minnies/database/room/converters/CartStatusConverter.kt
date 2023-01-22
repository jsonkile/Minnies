package com.demo.minnies.database.room.converters

import androidx.room.TypeConverter
import com.demo.minnies.database.models.CartStatus
import com.demo.minnies.database.models.Category

class CartStatusConverter {
    @TypeConverter
    fun stringToStatus(status: String) = CartStatus.valueOf(status)

    @TypeConverter
    fun statusToString(cartStatus: CartStatus) = cartStatus.name
}