package wtf.speech.feature.passcode.ui.create

import wtf.speech.core.ui.ContentState
import wtf.speech.feature.passcode.ui.BasePasscodeViewModel
import wtf.speech.feature.passcode.ui.MAX_PASSCODE_SIZE
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.feature.passcode.ui.PasscodeScreenState

internal class CreatePasscodeViewModel(state: PasscodeScreenState = PasscodeScreenState()) : BasePasscodeViewModel(state) {

    override fun checkPasscode(passcode: List<Int>): PasscodeScreenEffect? {
        return if (passcode.size == MAX_PASSCODE_SIZE) {
            PasscodeScreenEffect.AuthSuccess(passcode)
        } else {
            null
        }
    }

    override fun PasscodeScreenState.onSuccess(passcode: List<Int>): PasscodeScreenState {
        return copy(contentState = ContentState.Success(Unit))
    }

    override fun PasscodeScreenState.onStartBiometricAuth() = this
}
