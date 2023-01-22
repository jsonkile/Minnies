package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.database.models.PartialUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCachedUserUseCase {
    operator fun invoke(): Flow<PartialUser?>
}

class GetCachedUserUseCaseImpl @Inject constructor(
    private val cacheRepo: CacheRepo
) : GetCachedUserUseCase {
    override operator fun invoke() = cacheRepo.getLoggedInUser()
}