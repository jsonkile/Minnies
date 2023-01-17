package com.demo.minnies.shared.data.repos

interface KeysRepository {
    
    suspend fun storeEncryptionSecretKey(key: String)

    suspend fun getEncryptionSecretKey(): String

    suspend fun storeEncryptionInitializationVectorKey(key: String)

    suspend fun getEncryptionInitializationVectorKey(): String
}