package wtf.speech.feature.passcode.ui.confirm

import wtf.speech.core.domain.models.DecryptedData
import wtf.speech.core.domain.usecases.UseCase
import wtf.speech.feature.passcode.ui.BasePasscodeViewModel
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.feature.passcode.ui.PasscodeScreenState
import wtf.speech.features.passcode.domain.usecase.CheckPasscodesEqualsUseCase

internal class ConfirmPasscodeViewModel(
    private val extras: ConfirmPasscodeScreen.ConfirmPasscodeExtra,
    private val checkPasscodeUseCase: UseCase<CheckPasscodesEqualsUseCase.Params, Boolean>,
    private val generateEncryptionKeyUseCase: UseCase<GenerateEncryptionKeyUseCase.Params, Boolean>
) : BasePasscodeViewModel(PasscodeScreenState()) {

    override fun checkPasscode(passcode: List<Int>): PasscodeScreenEffect? {

        return when (isPasscodesEquals(passcode)) {
            true -> {
                val encryptionKey: DecryptedData = createEncryptionKey()

                PasscodeScreenEffect.EnterPasscodeSuccess(encryptionKey)
            }

            else -> PasscodeScreenEffect.WrongPasscode
        }
    }

    private fun createEncryptionKey(): DecryptedData {

    }

    private fun isPasscodesEquals(passcode: List<Int>) = checkPasscodeUseCase(CheckPasscodesEqualsUseCase.Params(extras.passcode, passcode))

    override fun PasscodeScreenState.onSuccess(passcode: List<Int>): PasscodeScreenState {
        return this
    }

    override fun PasscodeScreenState.onStartBiometricAuth(): PasscodeScreenState = this
}
