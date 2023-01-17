package com.demo.minnies.shared.utils.encryption

import com.demo.minnies.shared.data.repos.KeysRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AESEncryptorImplTest {

    private val keysRepo = object : KeysRepository {
        override suspend fun storeEncryptionSecretKey(key: String) {}

        override suspend fun getEncryptionSecretKey() = "sVwnNYYk5ve77Y%0"

        override suspend fun storeEncryptionInitializationVectorKey(key: String) {}

        override suspend fun getEncryptionInitializationVectorKey() = "m@4*dI723!UX@IVF"
    }

    private val aesEncryptorImpl = AESEncryptorImpl(keysRepo)
    private val aesEncryptorImpl2 = AESEncryptorImpl(keysRepo)

    @Test
    fun `test that after calling encrypt on a value, call decrypt on the result returns the original value`() =
        runTest {
            val stringToEncrypt = "test value"
            val encryptionResult = aesEncryptorImpl2.encrypt(stringToEncrypt)

            print(encryptionResult)

            assertEquals(
                "test value",
                aesEncryptorImpl.decrypt(encryptionResult)
            )

            assertNotEquals(
                "test value fake",
                aesEncryptorImpl.decrypt(encryptionResult)
            )
        }
}