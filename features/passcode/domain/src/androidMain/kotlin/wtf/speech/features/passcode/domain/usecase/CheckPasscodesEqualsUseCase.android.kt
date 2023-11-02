package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.CipherUtils
import wtf.speech.core.domain.usecases.UseCase
import javax.crypto.Cipher

actual class CheckPasscodesEqualsUseCase actual constructor() : UseCase<CheckPasscodesEqualsUseCaseParams, Boolean>() {
    override fun invoke(input: CheckPasscodesEqualsUseCaseParams): Boolean {
        val secretKey = CipherUtils.getSecretKeyFromPassword(
            input.enteredPasscode.joinToString(separator = "") { it.toString() },
            byteArrayOf()
        )

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        return try {
            cipher.doFinal(input.encryptedData.data)
            true
        } catch (e: Exception) {
            false
        }
    }
}
