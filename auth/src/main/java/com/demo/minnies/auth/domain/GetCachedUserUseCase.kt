package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import javax.inject.Inject

class GetCachedUserUseCase @Inject constructor(
    private val cacheRepo: CacheRepo
) {
    operator fun invoke() = cacheRepo.getLoggedInUser()
}