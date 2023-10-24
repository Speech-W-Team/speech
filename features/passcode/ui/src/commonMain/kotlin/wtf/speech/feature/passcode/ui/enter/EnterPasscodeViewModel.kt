package wtf.speech.feature.passcode.ui.enter

import wtf.speech.core.domain.usecases.UseCase
import wtf.speech.feature.passcode.ui.BasePasscodeViewModel
import wtf.speech.feature.passcode.ui.PasscodeScreenEvent
import wtf.speech.feature.passcode.ui.PasscodeScreenState
import wtf.speech.features.passcode.domain.usecase.CheckPasscodesEqualsUseCase

internal class EnterPasscodeViewModel(
    private val checkPasscodeUseCase: UseCase<CheckPasscodesEqualsUseCase.Params, Boolean>
) : BasePasscodeViewModel(PasscodeScreenState()) {

    override fun checkPasscode(passcode: List<Int>): PasscodeScreenEvent {
        return checkPasscodeUseCase(CheckPasscodesEqualsUseCase.Params(listOf(1,1,1,1,1,1), passcode))
            .let { isEquals ->
                if (isEquals) {
                    PasscodeScreenEvent.Success(passcode)
                } else {
                    PasscodeScreenEvent.NonEqualsPasscodes
                }
            }
    }

    override fun PasscodeScreenState.onSuccess(passcode: List<Int>): PasscodeScreenState {
        return this
    }

    override fun PasscodeScreenState.onStartBiometricAuth(): PasscodeScreenState {
        return this
    }
}
