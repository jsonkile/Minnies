package com.demo.minnies.shared.utils.encryption

import com.demo.minnies.shared.data.repos.KeysPreferencesRepository
import com.demo.minnies.shared.utils.customString
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class AESEncryptorImpl @Inject constructor(
    private val keysPreferencesRepository: KeysPreferencesRepository
) : Encryptor {

    override suspend fun encrypt(data: String): String {
        val plainText = data.toByteArray(Charsets.UTF_8)
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        val key = keygen.generateKey()
        saveSecretKey(key)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)
        saveInitializationVector(cipher.iv)

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toChar())
        }

        return cipherText.customString()
    }

    override suspend fun decrypt(data: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(getSavedInitializationVector())
        cipher.init(Cipher.DECRYPT_MODE, getSavedSecretKey(), ivSpec)
        val cipherText = cipher.doFinal(data)

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toChar())
        }

        return sb.toString()
    }

    private fun saveSecretKey(secretKey: SecretKey) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val oos = ObjectOutputStream(byteArrayOutputStream)
        oos.writeObject(secretKey)
        val strToSave =
            String(
                android.util.Base64.encode(
                    byteArrayOutputStream.toByteArray(), android.util.Base64.DEFAULT
                )
            )
        runBlocking {
            keysPreferencesRepository.storeEncryptionSecretKey(strToSave)
        }
    }

    private fun getSavedSecretKey(): SecretKey {
        val strSecretKey = runBlocking { keysPreferencesRepository.getEncryptionSecretKey() }
        val bytes = android.util.Base64.decode(strSecretKey, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        return ois.readObject() as SecretKey
    }

    private fun saveInitializationVector(initializationVector: ByteArray) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val oos = ObjectOutputStream(byteArrayOutputStream)
        oos.writeObject(initializationVector)
        val strToSave =
            String(
                android.util.Base64.encode(
                    byteArrayOutputStream.toByteArray(),
                    android.util.Base64.DEFAULT
                )
            )
        runBlocking {
            keysPreferencesRepository.storeEncryptionInitializationVectorKey(strToSave)
        }
    }

    private fun getSavedInitializationVector(): ByteArray {
        val strInitializationVector =
            runBlocking { keysPreferencesRepository.getEncryptionInitializationVectorKey() }
        val bytes = android.util.Base64.decode(strInitializationVector, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        return ois.readObject() as ByteArray
    }
}