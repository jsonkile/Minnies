package com.demo.minnies.presentation.auth

import android.content.Context
import androidx.compose.ui.test.*
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
import com.demo.minnies.auth.presentation.components.SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG
import com.demo.minnies.auth.presentation.components.SIGN_IN_PROMPT_TEST_TAG
import com.demo.minnies.auth.presentation.screens.*
import com.demo.minnies.auth.presentation.screens.account.SHIPPING_ADDRESS_TEXT_FIELD_TEST_TAG
import com.demo.minnies.auth.presentation.screens.account.SUBMIT_BUTTON_TEST_TAG
import com.demo.minnies.auth.presentation.screens.account.UPDATE_SHIPPING_ADDRESS_BUTTON
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.UserSerializer
import com.demo.minnies.presentation.MainActivity
import com.demo.minnies.presentation.screens.ACCOUNT_BUTTON_TEST_TAG
import com.demo.minnies.presentation.screens.LOGOUT_BUTTON_TEST_TAG
import com.demo.minnies.shared.utils.getRandomString
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(DataStoreModule::class, com.demo.minnies.shared.di.DataStoreModule::class)
@HiltAndroidTest
class UpdateShippingAddressTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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
        }
    )

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
    fun updateAddressTest() = runTest {
        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertIsDisplayed()

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG).performClick()

        composeTestRule.onNodeWithTag(FULL_NAME_TEXT_FIELD_TEST_TAG)
            .performTextInput("Jayson Kile")
        val email = "${getRandomString(10)}@email.com"
        val phone = "90774343434"
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_TEST_TAG)
            .performTextInput(email)
        composeTestRule.onNodeWithTag(PHONE_TEXT_FIELD_TEST_TAG).performTextInput(phone)
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD_TEST_TAG).performTextInput("Test@123")
        composeTestRule.onNodeWithTag(CONFIRM_PASSWORD_TEXT_FIELD_TEST_TAG)
            .performTextInput("Test@123")

        composeTestRule.onNodeWithTag(REGISTER_BUTTON_TEST_TAG).performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithTag(REGISTER_SCREEN_CONTAINER_TEST_TAG)
                .fetchSemanticsNodes()
                .isEmpty()
        }

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription("More page button").performClick()

        composeTestRule.onNodeWithTag(ACCOUNT_BUTTON_TEST_TAG).performClick()

        composeTestRule.onNodeWithText("Jayson Kile").assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(phone).assertIsDisplayed()

        composeTestRule.onNodeWithTag(UPDATE_SHIPPING_ADDRESS_BUTTON).performClick()

        composeTestRule.onNodeWithTag(SHIPPING_ADDRESS_TEXT_FIELD_TEST_TAG)
            .performTextInput("my shipping address")
        composeTestRule.onNodeWithTag(SUBMIT_BUTTON_TEST_TAG).performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithText("-").fetchSemanticsNodes()
                .isEmpty()
        }

        composeTestRule.onAllNodesWithText(text = "my shipping address", substring = false)
            .assertCountEquals(2)

        composeTestRule.onNodeWithContentDescription("back button").performClick()

        composeTestRule.onNodeWithTag(LOGOUT_BUTTON_TEST_TAG).performClick()

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertIsDisplayed()
    }
}