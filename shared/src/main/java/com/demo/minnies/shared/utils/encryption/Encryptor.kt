package com.demo.minnies.shared.utils.encryption

interface Encryptor {
    suspend fun encrypt(data: String): String

    suspend fun decrypt(data: ByteArray): String
}