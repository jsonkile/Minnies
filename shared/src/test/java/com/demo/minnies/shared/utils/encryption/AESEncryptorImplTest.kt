package com.demo.minnies.shared.utils.encryption

import com.demo.minnies.shared.data.repos.KeysPreferencesRepository
import com.demo.minnies.shared.utils.customByteArray
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AESEncryptorImplTest {

    val keyHolder = mutableMapOf<String, String>()
    private val keysRepo = object : KeysPreferencesRepository {
        override suspend fun storeEncryptionSecretKey(key: String) {
            keyHolder["secret"] = key
        }

        override suspend fun getEncryptionSecretKey() = keyHolder["secret"].orEmpty()

        override suspend fun storeEncryptionInitializationVectorKey(key: String) {
            keyHolder["vector"] = key
        }

        override suspend fun getEncryptionInitializationVectorKey() = keyHolder["vector"].orEmpty()

    }

    private val aesEncryptorImplTest = AESEncryptorImpl(keysRepo)

    @Test
    fun `test that after calling encrypt on a value, call decrypt on the result returns the original value`() =
        runTest {
            val stringToEncrypt = "test value"
            val encryptionResult = aesEncryptorImplTest.encrypt(stringToEncrypt)

            println(encryptionResult)

            assertEquals(
                "test value",
                aesEncryptorImplTest.decrypt(encryptionResult.customByteArray())
            )
            assertNotEquals(
                "test value fake",
                aesEncryptorImplTest.decrypt(encryptionResult.customByteArray())
            )
        }


}