package com.demo.minnies.shared.data.repos

import com.demo.minnies.shared.utils.Currency
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class UserPreferencesRepositoryImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun setsAndGets_NotificationPreference_toDataStore() = runTest {
        userPreferencesRepository.setNotificationsPreference(false)
        Assert.assertFalse(userPreferencesRepository.getNotificationsPreference().first())
    }

    @Test
    fun setsAndGets_CurrencyPreference_toDataStore() = runTest {
        userPreferencesRepository.setCurrencyPreference(Currency.EUR.name)
        Assert.assertEquals(
            Currency.EUR.name,
            userPreferencesRepository.getCurrencyPreference().first()
        )

        userPreferencesRepository.setCurrencyPreference(Currency.USD.name)
        Assert.assertEquals(
            Currency.USD.name,
            userPreferencesRepository.getCurrencyPreference().first()
        )
    }
}