package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.ShippingAddress
import javax.inject.Inject

interface UpdateUserShippingAddressUseCase {
    suspend operator fun invoke(shippingAddress: ShippingAddress): PartialUser?
}

class UpdateUserShippingAddressUseCaseImpl @Inject constructor(private val userRepo: UserRepo) :
    UpdateUserShippingAddressUseCase {
    override suspend fun invoke(shippingAddress: ShippingAddress) =
        userRepo.updateUserShippingAddress(shippingAddress)
}