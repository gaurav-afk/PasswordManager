package com.towerofapp.passwordsaver.util

import android.os.Build
import android.util.Base64
import java.nio.ByteBuffer
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object CryptoManager {
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val KEY_ALGORITHM = "AES"
    private const val IV_LENGTH_BYTES = 12 // recommended for GCM
    private const val TAG_LENGTH_BITS = 128

    fun getOrCreateSecretKey(alias: String): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }

        // If key exists in keystore, return it
        val existing = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry
        if (existing != null) return existing.secretKey

        // Create new AES key in AndroidKeyStore
        val keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, ANDROID_KEYSTORE)
        val specBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            android.security.keystore.KeyGenParameterSpec.Builder(
                alias,
                android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or
                        android.security.keystore.KeyProperties.PURPOSE_DECRYPT
            ).run {
                setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
                setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
                setKeySize(256)
                setUserAuthenticationRequired(false) // set true if you want auth for key use
                build()
            }
        } else {
            throw IllegalStateException("API 23+ required for AndroidKeyStore AES keys")
        }

        keyGen.init(specBuilder)
        return keyGen.generateKey()
    }

    /**
     * Encrypts plaintext (UTF-8) and returns Base64( IV || ciphertext )
     */
    fun encrypt(alias: String, plainText: String): String {
        val secretKey = getOrCreateSecretKey(alias)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv // 12 bytes normally
        val cipherText = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        // store iv + ciphertext (iv length is fixed)
        val combined = ByteBuffer.allocate(iv.size + cipherText.size)
            .put(iv)
            .put(cipherText)
            .array()

        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    /**
     * Decrypts Base64( IV || ciphertext ) back to plaintext UTF-8
     */
    fun decrypt(alias: String, base64IvAndCiphertext: String): String {
        val secretKey = getOrCreateSecretKey(alias)
        val combined = Base64.decode(base64IvAndCiphertext, Base64.NO_WRAP)

        if (combined.size < IV_LENGTH_BYTES) throw IllegalArgumentException("Invalid data")

        val iv = combined.copyOfRange(0, IV_LENGTH_BYTES)
        val cipherText = combined.copyOfRange(IV_LENGTH_BYTES, combined.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(TAG_LENGTH_BITS, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val plain = cipher.doFinal(cipherText)
        return String(plain, Charsets.UTF_8)
    }
}
