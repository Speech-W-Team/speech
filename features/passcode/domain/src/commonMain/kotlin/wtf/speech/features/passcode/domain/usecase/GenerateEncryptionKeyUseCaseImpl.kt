package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

interface GenerateEncryptionKeyUseCase :
    CoroutineUseCase<GenerateEncryptionKeyUseCase.Param, EncryptionSecretKey> {
    data class Param(val passcode: List<Int>)
}

fun provideGenerateEncryptionKeyUseCase(): GenerateEncryptionKeyUseCase {
    return GenerateEncryptionKeyUseCaseImpl()
}

internal class GenerateEncryptionKeyUseCaseImpl : GenerateEncryptionKeyUseCase {

    override suspend fun invoke(input: GenerateEncryptionKeyUseCase.Param): EncryptionSecretKey {
        return EncryptionUtils.generateEncryptionKey(input.passcode)
    }
}
