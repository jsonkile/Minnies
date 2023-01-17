package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.AuthRepo
import com.demo.minnies.auth.data.repos.CacheRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface LoginUserUseCase {
    suspend operator fun invoke(email: String, password: String)
}

class LoginUserUseCaseImpl @Inject constructor(
    private val authRepo: AuthRepo,
    private val cacheRepo: CacheRepo
) : LoginUserUseCase {
    override suspend operator fun invoke(email: String, password: String) {
        val user = authRepo.login(email, password).first()
        cacheRepo.storeLoggedInUser(user!!)
    }
}