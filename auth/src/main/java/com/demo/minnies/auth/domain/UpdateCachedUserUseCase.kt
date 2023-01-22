package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.database.models.PartialUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateCachedUserUseCase {
    suspend operator fun invoke(user: PartialUser)
}

class UpdateCachedUserUseCaseImpl @Inject constructor(private val cacheRepo: CacheRepo) : UpdateCachedUserUseCase {
    override suspend fun invoke(user: PartialUser) = cacheRepo.storeLoggedInUser(user)
}