package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.CipherUtils
import wtf.speech.core.domain.repository.Repository
import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

actual class GenerateEncryptionKeyUseCase(private val repository: Repository<EncryptionSecretKey>) :
    CoroutineUseCase<GenerateEncryptionKeyParam, EncryptionSecretKey> {

    override suspend fun invoke(input: GenerateEncryptionKeyParam): EncryptionSecretKey {
        val secretKey = CipherUtils.getSecretKeyFromPassword(
            input.passcode.joinToString(separator = "") { it.toString() },
            byteArrayOf()
        )

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        val encryptionSecretKey = keyGen.generateKey()

        return EncryptionSecretKey(encryptionSecretKey.encoded)
    }
}