package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.AuthRepo
import com.demo.minnies.auth.data.repos.CacheRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepo: AuthRepo,
    private val cacheRepo: CacheRepo
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ) {
        val user = authRepo.register(
            emailAddress = email,
            password = password,
            fullName = fullName,
            phoneNumber = phoneNumber
        ).first()

        user?.let {
            cacheRepo.storeLoggedInUser(user)
        }
    }
}