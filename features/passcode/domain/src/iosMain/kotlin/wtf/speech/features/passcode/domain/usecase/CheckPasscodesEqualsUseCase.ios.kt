package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.usecases.UseCase

actual class CheckPasscodesEqualsUseCase actual constructor() : UseCase<CheckPasscodesEqualsUseCaseParams, Boolean>() {

    override fun invoke(input: CheckPasscodesEqualsUseCaseParams): Boolean {


        return true
    }
}
