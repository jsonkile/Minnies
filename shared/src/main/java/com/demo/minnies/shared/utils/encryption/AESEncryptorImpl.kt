package com.demo.minnies.shared.utils.encryption

import android.util.Base64
import com.demo.minnies.shared.data.repos.KeysRepository
import com.demo.minnies.shared.utils.PREFERENCE_INITIALIZATION_VECTOR_KEY
import com.demo.minnies.shared.utils.PREFERENCE_SECRET_KEY_KEY
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class AESEncryptorImpl @Inject constructor(private val keysRepository: KeysRepository) : Encryptor {

    override suspend fun encrypt(data: String): String {
        try {
            val iv =
                IvParameterSpec(
                    keysRepository.getEncryptionInitializationVectorKey()
                        .toByteArray(charset("UTF-8"))
                )
            val keySpec =
                SecretKeySpec(
                    keysRepository.getEncryptionSecretKey().toByteArray(charset("UTF-8")),
                    "AES"
                )
            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
            val encrypted: ByteArray = cipher.doFinal(data.toByteArray(charset("UTF-8")))
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    override suspend fun decrypt(data: String): String {
        try {
            val iv =
                IvParameterSpec(
                    keysRepository.getEncryptionInitializationVectorKey()
                        .toByteArray(charset("UTF-8"))
                )
            val keySpec =
                SecretKeySpec(
                    keysRepository.getEncryptionSecretKey().toByteArray(charset("UTF-8")),
                    "AES"
                )
            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
            val original: ByteArray = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))
            return String(original)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""
    }
}