package wtf.speech.core.domain

import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


object CipherUtils {

    private const val DEFAULT_ITERATIONS_COUNT = 1_000_000

    const val KEY_LENGTH_AES256 = 256

    fun getSecretKeyFromPassword(
        password: String,
        salt: ByteArray,
        iterations: Int = DEFAULT_ITERATIONS_COUNT
    ): SecretKey {
        val keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keySpec: KeySpec =
            PBEKeySpec(password.toCharArray(), salt, iterations, KEY_LENGTH_AES256)
        val secretKey = keyFactory.generateSecret(keySpec)

        return SecretKeySpec(secretKey.encoded, "AES")
    }
}