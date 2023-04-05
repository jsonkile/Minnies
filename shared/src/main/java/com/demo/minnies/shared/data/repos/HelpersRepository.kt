package com.demo.minnies.shared.data.repos

import javax.inject.Inject

interface HelpersRepository {

    suspend fun getDeliveryFee(): Double
}


class HelpersRepositoryHardImpl @Inject constructor() : HelpersRepository {

    override suspend fun getDeliveryFee(): Double = 20.0
}