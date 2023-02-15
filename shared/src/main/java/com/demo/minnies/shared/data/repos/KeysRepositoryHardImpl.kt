package com.demo.minnies.shared.data.repos

import javax.inject.Inject

const val ENCRYPTION_SECRET_KEY_HARDCODED = "56Z21m*d*5Nfgo7P"
const val ENCRYPTION_INIT_VECTOR_KEY_HARDCODED = "*Z9vYC%E1r8Jw4s4"

class KeysRepositoryHardImpl @Inject constructor() : KeysRepository {
    override suspend fun storeEncryptionSecretKey(key: String) {}

    override suspend fun getEncryptionSecretKey() = ENCRYPTION_SECRET_KEY_HARDCODED

    override suspend fun storeEncryptionInitializationVectorKey(key: String) {}

    override suspend fun getEncryptionInitializationVectorKey() =
        ENCRYPTION_INIT_VECTOR_KEY_HARDCODED
}