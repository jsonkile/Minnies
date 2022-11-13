package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepoRoomImpl @Inject constructor(private val userRepo: UserRepo) : AuthRepo {

    override suspend fun login(emailAddress: String, password: String) =
        userRepo.getUser(emailAddress, password)

    override suspend fun register(
        emailAddress: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Flow<PartialUser?> = userRepo.getUser(
        userRepo.addUser(
            User(
                emailAddress = emailAddress,
                password = password,
                fullName = fullName,
                phoneNumber = phoneNumber
            )
        )
    )
}