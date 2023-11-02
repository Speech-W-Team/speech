package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

expect class GenerateEncryptionKeyUseCase: CoroutineUseCase<GenerateEncryptionKeyParam, EncryptionSecretKey>

data class GenerateEncryptionKeyParam(val passcode: List<Int>)