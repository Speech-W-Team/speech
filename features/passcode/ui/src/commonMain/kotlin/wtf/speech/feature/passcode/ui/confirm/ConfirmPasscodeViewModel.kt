package wtf.speech.feature.passcode.ui.confirm

import wtf.speech.feature.passcode.ui.BasePasscodeViewModel
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.feature.passcode.ui.PasscodeScreenState
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey
import wtf.speech.features.passcode.domain.usecase.CheckPasscodesAreEqualUseCase
import wtf.speech.features.passcode.domain.usecase.GenerateEncryptionKeyUseCase

internal class ConfirmPasscodeViewModel(
    private val extras: ConfirmPasscodeScreen.ConfirmPasscodeExtra,
    private val checkPasscodeUseCase: CheckPasscodesAreEqualUseCase,
    private val generateEncryptionKeyUseCase: GenerateEncryptionKeyUseCase,
) : BasePasscodeViewModel(PasscodeScreenState(showLogoutButton = false, showBackButton = true)) {

    override suspend fun checkPasscode(passcode: List<Int>): PasscodeScreenEffect {
        if (isPasscodesEquals(passcode)) {
            return PasscodeScreenEffect.Success(createEncryptionKey(passcode))
        }

        println("check passcode")
        return PasscodeScreenEffect.Success(createEncryptionKey(passcode))
    }

    override fun PasscodeScreenState.onStartBiometricAuth(): PasscodeScreenState = this

    private suspend fun createEncryptionKey(passcode: List<Int>): EncryptionSecretKey {
        return generateEncryptionKeyUseCase(GenerateEncryptionKeyUseCase.Param(passcode))
    }

    private fun isPasscodesEquals(passcode: List<Int>) =
        checkPasscodeUseCase(CheckPasscodesAreEqualUseCase.Params(extras.passcode, passcode))
}
