package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.models.DecryptedData
import wtf.speech.core.domain.usecases.CoroutineUseCase

actual class GenerateEncryptionKeyUseCase :
    CoroutineUseCase<GenerateEncryptionKeyParam, DecryptedData>() {
}