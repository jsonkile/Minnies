package com.demo.minnies.shared.domain

import com.demo.minnies.shared.data.repos.HelpersRepository
import com.demo.minnies.shared.data.repos.UserPreferencesRepository
import javax.inject.Inject

class FetchDeliveryFeeUseCase @Inject constructor(private val helpersRepository: HelpersRepository) {
    suspend operator fun invoke() = helpersRepository.getDeliveryFee()
}