package com.demo.minnies.notifications.domain

import com.demo.minnies.shared.data.repos.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPushNotificationsPreferenceUseCase @Inject constructor(private val repo: UserPreferencesRepository) {
    operator fun invoke(): Flow<Boolean> = repo.getNotificationsPreference()
}