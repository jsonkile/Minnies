package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.database.room.models.PartialUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUserUseCase {
    operator fun invoke(id: Long): Flow<PartialUser?>
}

class GetUserUseCaseImpl @Inject constructor(private val userRepo: UserRepo) : GetUserUseCase {
    override fun invoke(id: Long): Flow<PartialUser?> = userRepo.getUser(id)
}