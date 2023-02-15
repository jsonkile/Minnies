package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.ShippingAddress
import com.demo.minnies.database.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getUser(emailAddress: String): Flow<PartialUser?>

    fun getUser(id: Long): Flow<PartialUser?>

    fun getUser(emailAddress: String, password: String): Flow<PartialUser?>

    suspend fun peekPassword(emailAddress: String): String?

    suspend fun addUser(user: User): Long

    suspend fun removeUser(partialUser: PartialUser): Int

    suspend fun updateUser(partialUser: PartialUser): Int

    suspend fun updateUserShippingAddress(shippingAddress: ShippingAddress): PartialUser?
}