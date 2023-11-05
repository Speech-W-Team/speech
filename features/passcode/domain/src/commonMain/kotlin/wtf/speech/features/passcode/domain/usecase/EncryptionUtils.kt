package wtf.speech.features.passcode.domain.usecase

import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

expect object EncryptionUtils {
    
    fun generateEncryptionKey(passcode: List<Int>): EncryptionSecretKey
}