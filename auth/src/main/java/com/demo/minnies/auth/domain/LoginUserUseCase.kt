package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.AuthRepo
import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.utils.CustomExceptions
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepo: AuthRepo,
    private val cacheRepo: CacheRepo
) {
    suspend operator fun invoke(email: String, password: String) {
        val user = authRepo.login(email, password).first()
        if (user == null) {
            throw CustomExceptions.NotFoundException("User not found")
        } else {
            cacheRepo.storeLoggedInUser(user)
        }
    }
}