package com.demo.minnies.database.room.converters

import androidx.room.TypeConverter
import com.demo.minnies.database.models.OrderStatus

class OrderStatusConverter {
    @TypeConverter
    fun stringToStatus(status: String) = OrderStatus.valueOf(status)

    @TypeConverter
    fun statusToString(orderStatus: OrderStatus) = orderStatus.name
}