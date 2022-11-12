package com.demo.minnies.shared.data.repos

interface KeysPreferencesRepository {
    
    suspend fun storeEncryptionSecretKey(key: String)

    suspend fun getEncryptionSecretKey(): String

    suspend fun storeEncryptionInitializationVectorKey(key: String)

    suspend fun getEncryptionInitializationVectorKey(): String
}