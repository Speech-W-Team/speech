package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.CipherUtils
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

actual object EncryptionUtils {
    actual fun generateEncryptionKey(passcode: List<Int>): EncryptionSecretKey {
        val secretKey = CipherUtils.getSecretKeyFromPassword(
            passcode.joinToString(separator = "") { it.toString() },
            byteArrayOf()
        )

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(CipherUtils.KEY_LENGTH_AES256)
        val encryptionSecretKey = keyGen.generateKey()

        return EncryptionSecretKey(encryptionSecretKey.encoded)
    }
}