package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import javax.inject.Inject

interface LogoutUserUseCase {
    suspend operator fun invoke()
}

class LogoutUserUseCaseImpl @Inject constructor(private val cacheRepo: CacheRepo) :
    LogoutUserUseCase {
    override suspend operator fun invoke() {
        cacheRepo.clear()
    }
}