package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.models.PartialUser
import kotlinx.coroutines.flow.Flow

interface CacheRepo {

    suspend fun storeLoggedInUser(user: PartialUser)

    fun getLoggedInUser() : Flow<PartialUser?>

    suspend fun clear()
}