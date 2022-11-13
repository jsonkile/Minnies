package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val cacheRepo: CacheRepo) {
    suspend operator fun invoke() {
        cacheRepo.clear()
    }
}