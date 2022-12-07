package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.User
import com.demo.minnies.shared.utils.customByteArray
import com.demo.minnies.shared.utils.encryption.Encryptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class AuthRepoRoomImpl @Inject constructor(
    private val userRepo: UserRepo,
    private val encryptor: Encryptor
) : AuthRepo {

    override suspend fun login(emailAddress: String, password: String): Flow<PartialUser?> {

        val user = userRepo.getUser(emailAddress).first()
        checkNotNull(user) { "The user with the details was not found." }

        Timber.e(userRepo.peekPassword(emailAddress)!!)
        print(userRepo.peekPassword(emailAddress)!!)

        val decryptedLoginPassword = encryptor.decrypt(password.customByteArray())
        val decryptedUserPassword =
            encryptor.decrypt(userRepo.peekPassword(emailAddress)!!.customByteArray())

        Timber.e("$decryptedLoginPassword $decryptedUserPassword")

        check(decryptedLoginPassword == decryptedUserPassword) {
            "The user with the details was not found."
        }

        return userRepo.getUser(emailAddress)
    }

    override suspend fun register(
        emailAddress: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Flow<PartialUser?> {

        check(userRepo.getUser(emailAddress).first() == null) {
            "A user with this email address already exists."
        }

        return userRepo.getUser(
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

}