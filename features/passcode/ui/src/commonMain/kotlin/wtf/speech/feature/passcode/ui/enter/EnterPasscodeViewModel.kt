package wtf.speech.feature.passcode.ui.enter

import wtf.speech.core.domain.usecases.UseCase
import wtf.speech.feature.passcode.ui.BasePasscodeViewModel
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.feature.passcode.ui.PasscodeScreenState
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey
import wtf.speech.features.passcode.domain.usecase.CheckPasscodesAreEqualUseCase

internal class EnterPasscodeViewModel(
    private val checkPasscodeUseCase: UseCase<CheckPasscodesAreEqualUseCase.Params, Boolean>
) : BasePasscodeViewModel(PasscodeScreenState()) {

    override suspend fun checkPasscode(passcode: List<Int>): PasscodeScreenEffect {
        if (checkPasscodeUseCase(
                CheckPasscodesAreEqualUseCase.Params(
                    listOf(1, 1, 1, 1, 1, 1),
                    passcode
                )
            )
        ) {
            return PasscodeScreenEffect.Success(EncryptionSecretKey(byteArrayOf()))
        }

        return PasscodeScreenEffect.InvalidPasscode
    }

    override fun PasscodeScreenState.onStartBiometricAuth() = this
}
