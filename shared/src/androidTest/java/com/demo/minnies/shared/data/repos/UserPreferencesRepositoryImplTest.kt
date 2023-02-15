package com.demo.minnies.shared.data.repos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.UserSerializer
import com.demo.minnies.shared.di.DataStoreModule
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.TEST_PREFERENCE_DATASTORE_FILE_NAME
import com.demo.minnies.shared.utils.TEST_PROTO_DATASTORE_FILE_NAME
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(DataStoreModule::class)
@HiltAndroidTest
internal class UserPreferencesRepositoryImplTest {

    val scope = TestScope(UnconfinedTestDispatcher(TestCoroutineScheduler()))

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @BindValue
    @JvmField
    val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = scope,
        produceFile = {
            ApplicationProvider.getApplicationContext<Context>()
                .preferencesDataStoreFile(TEST_PREFERENCE_DATASTORE_FILE_NAME)
        }
    )

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun clean() {
        scope.cancel()
    }

    @Test
    fun testSetAndGetPreferences() = runTest {
        userPreferencesRepository.setNotificationsPreference(false)
        Assert.assertFalse(userPreferencesRepository.getNotificationsPreference().first())

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