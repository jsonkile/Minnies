package com.demo.minnies.shared.data.repos

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class KeysPreferencesRepositoryDataStoreImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var keysPreferencesRepository: KeysPreferencesRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun storeEncryptionKey_And_GetEncryptionSecretKey_WorkCorrectly() = runTest {
        keysPreferencesRepository.storeEncryptionSecretKey("test key")
        assertEquals("test key", keysPreferencesRepository.getEncryptionSecretKey())
        assertNotEquals("", keysPreferencesRepository.getEncryptionSecretKey())
    }
}