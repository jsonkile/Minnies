package com.demo.minnies.presentation.auth

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.demo.minnies.auth.di.DataStoreModule
import com.demo.minnies.auth.presentation.components.SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG
import com.demo.minnies.auth.presentation.components.SIGN_IN_PROMPT_TEST_TAG
import com.demo.minnies.auth.presentation.components.SIGN_IN_PROMPT__LOGIN_BUTTON_TEST_TAG
import com.demo.minnies.auth.presentation.screens.*
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.UserSerializer
import com.demo.minnies.presentation.MainActivity
import com.demo.minnies.presentation.screens.ACCOUNT_BUTTON_TEST_TAG
import com.demo.minnies.presentation.screens.LOGOUT_BUTTON_TEST_TAG
import com.demo.minnies.shared.utils.TEST_PREFERENCE_DATASTORE_FILE_NAME
import com.demo.minnies.shared.utils.TEST_PROTO_DATASTORE_FILE_NAME
import com.demo.minnies.shared.utils.getRandomString
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(DataStoreModule::class, com.demo.minnies.shared.di.DataStoreModule::class)
@HiltAndroidTest
class AuthenticationTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val scope = TestScope(UnconfinedTestDispatcher(TestCoroutineScheduler()))

    @BindValue
    @JvmField
    val protoDataStore: DataStore<PartialUser?> = DataStoreFactory.create(
        serializer = UserSerializer,
        produceFile = {
            ApplicationProvider.getApplicationContext<Context>()
                .dataStoreFile(TEST_PROTO_DATASTORE_FILE_NAME)
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
                .preferencesDataStoreFile(TEST_PREFERENCE_DATASTORE_FILE_NAME)
        })

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun clean() {
        scope.cancel()
    }

    /**
     * Test auth flow
     * Create a new account, confirm details of the account on account screen,
     * logout, login to the same account and the finally logout
     */
    @Test
    fun registerUserTest() = runTest {
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

        composeTestRule.onNodeWithContentDescription("back button").performClick()


        composeTestRule.onNodeWithTag(LOGOUT_BUTTON_TEST_TAG).performClick()

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertIsDisplayed()

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT__LOGIN_BUTTON_TEST_TAG).performClick()

        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_TEST_TAG)
            .performTextInput(email)
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD_TEST_TAG).performTextInput("Test@123")

        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TEST_TAG).performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithTag(LOGIN_SCREEN_CONTAINER_TEST_TAG)
                .fetchSemanticsNodes()
                .isEmpty()
        }

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertDoesNotExist()

        composeTestRule.onNodeWithTag(LOGOUT_BUTTON_TEST_TAG).performClick()

        composeTestRule.onNodeWithTag(SIGN_IN_PROMPT_TEST_TAG).assertIsDisplayed()
    }
}