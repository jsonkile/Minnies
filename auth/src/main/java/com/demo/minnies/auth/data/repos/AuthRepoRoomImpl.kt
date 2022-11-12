package com.demo.minnies.auth.data.repos

import com.demo.minnies.auth.data.models.PartialUser
import com.demo.minnies.auth.data.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepoRoomImpl @Inject constructor(private val userRepo: UserRepo) : AuthRepo {

    override suspend fun login(emailAddress: String, password: String) =
        userRepo.getUser(emailAddress, password)

    override suspend fun register(
        emailAddress: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Flow<PartialUser?> {

        userRepo.addUser(
            User(
                emailAddress = emailAddress,
                password = password,
                fullName = fullName,
                phoneNumber = phoneNumber
            )
        )

        return userRepo.getUser(emailAddress)
    }
}