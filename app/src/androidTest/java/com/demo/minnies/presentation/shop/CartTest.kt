package com.demo.minnies.presentation.shop

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.demo.minnies.auth.di.DataStoreModule
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.UserSerializer
import com.demo.minnies.presentation.LoginLogoutTestRule
import com.demo.minnies.presentation.MainActivity
import com.demo.minnies.shared.DummyActivity
import com.demo.minnies.shared.utils.TEST_PROTO_DATASTORE_FILE_NAME
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(DataStoreModule::class, com.demo.minnies.shared.di.DataStoreModule::class)
@HiltAndroidTest
class CartTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

//    @get:Rule(order = 2)
//    val loginLogoutTestRule = LoginLogoutTestRule(composeRule)

    private val dispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    private val scope = TestScope(dispatcher + Job())

    @BindValue
    @JvmField
    val protoDataStore: DataStore<PartialUser?> = DataStoreFactory.create(
        serializer = UserSerializer,
        produceFile = {
            ApplicationProvider.getApplicationContext<Context>()
                .dataStoreFile(this::class.simpleName.orEmpty())
        },
        corruptionHandler = null,
        migrations = emptyList(),
        scope = scope
    )

    @BindValue
    @JvmField
    val preferenceDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = scope,
        produceFile = {
            ApplicationProvider.getApplicationContext<Context>()
                .preferencesDataStoreFile(this::class.simpleName.orEmpty())
        })

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun clean() {
        scope.runTest {
            preferenceDataStore.edit { it.clear() }
            protoDataStore.updateData { null }
        }
        scope.cancel()
    }

    @Test
    fun testCartFlow() = runTest {
        Assert.assertFalse(false)
    }

}