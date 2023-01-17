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
class KeysRepositoryDataStoreImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var keysRepository: KeysRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun storeEncryptionKey_And_GetEncryptionSecretKey_WorkCorrectly() = runTest {
        keysRepository.storeEncryptionSecretKey("test key")
        assertEquals("test key", keysRepository.getEncryptionSecretKey())
        assertNotEquals("", keysRepository.getEncryptionSecretKey())
    }
}