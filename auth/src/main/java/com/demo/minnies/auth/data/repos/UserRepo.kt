package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.ShippingAddress
import com.demo.minnies.database.room.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getUser(emailAddress: String): Flow<PartialUser?>

    fun getUser(id: Long): Flow<PartialUser?>

    fun getUser(emailAddress: String, password: String): Flow<PartialUser?>

    fun peekPassword(emailAddress: String): String?

    fun addUser(user: User): Long

    fun removeUser(partialUser: PartialUser): Int

    fun updateUser(partialUser: PartialUser): Int

    suspend fun updateUserShippingAddress(shippingAddress: ShippingAddress): PartialUser?
}