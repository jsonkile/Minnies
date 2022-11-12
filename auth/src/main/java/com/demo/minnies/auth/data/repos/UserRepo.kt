package com.demo.minnies.auth.data.repos

import com.demo.minnies.auth.data.models.PartialUser
import com.demo.minnies.auth.data.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getUser(emailAddress: String): Flow<PartialUser?>

    fun getUser(emailAddress: String, password: String): Flow<PartialUser?>

    fun addUser(user: User): Long

    fun removeUser(partialUser: PartialUser): Int

    fun updateUser(partialUser: PartialUser): Int
}