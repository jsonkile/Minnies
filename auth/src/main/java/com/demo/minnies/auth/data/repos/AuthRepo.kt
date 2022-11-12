package com.demo.minnies.auth.data.repos

import com.demo.minnies.auth.data.models.PartialUser
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    suspend fun login(emailAddress: String, password: String): Flow<PartialUser?>

    suspend fun register(
        emailAddress: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Flow<PartialUser?>
}