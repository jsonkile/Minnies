package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.shared.utils.BAD_LOGIN_MESSAGE
import com.demo.minnies.shared.utils.encryption.Encryptor
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface LoginUserUseCase {
    suspend operator fun invoke(email: String, password: String)
}

class LoginUserUseCaseImpl @Inject constructor(
    private val userRepo: UserRepo,
    private val cacheRepo: CacheRepo,
    private val encryptor: Encryptor
) : LoginUserUseCase {
    override suspend operator fun invoke(email: String, password: String) {
        val user = userRepo.getUser(email).first()

        checkNotNull(user) { BAD_LOGIN_MESSAGE }

        val decryptedLoginPassword = encryptor.decrypt(password)

        val decryptedUserPassword = encryptor.decrypt(userRepo.peekPassword(email)!!)

        check(decryptedLoginPassword == decryptedUserPassword) { BAD_LOGIN_MESSAGE }

        cacheRepo.storeLoggedInUser(user)
    }
}