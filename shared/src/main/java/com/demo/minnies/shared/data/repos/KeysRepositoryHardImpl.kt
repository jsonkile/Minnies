package com.demo.minnies.shared.data.repos

import javax.inject.Inject

class KeysRepositoryHardImpl @Inject constructor() : KeysRepository {
    override suspend fun storeEncryptionSecretKey(key: String) {}

    override suspend fun getEncryptionSecretKey() = "56Z21m*d*5Nfgo7P"

    override suspend fun storeEncryptionInitializationVectorKey(key: String) {}

    override suspend fun getEncryptionInitializationVectorKey() = "*Z9vYC%E1r8Jw4s4"
}