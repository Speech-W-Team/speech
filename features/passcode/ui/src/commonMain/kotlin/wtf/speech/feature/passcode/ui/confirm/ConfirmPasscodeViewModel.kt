package wtf.speech.feature.passcode.ui.confirm

import wtf.speech.core.domain.usecases.UseCase
import wtf.speech.feature.passcode.ui.BasePasscodeViewModel
import wtf.speech.feature.passcode.ui.PasscodeScreenEvent
import wtf.speech.feature.passcode.ui.PasscodeScreenState
import wtf.speech.features.passcode.domain.usecase.CheckPasscodesEqualsUseCase

internal class ConfirmPasscodeViewModel(
    private val extras: ConfirmPasscodeScreen.Extra,
    private val checkPasscodeUseCase: UseCase<CheckPasscodesEqualsUseCase.Params, Boolean>
) : BasePasscodeViewModel(PasscodeScreenState()) {

    override fun checkPasscode(passcode: List<Int>): PasscodeScreenEvent {
        return checkPasscodeUseCase(CheckPasscodesEqualsUseCase.Params(extras.passcode, passcode))
            .let { isEquals ->
                if (isEquals) {
                    PasscodeScreenEvent.Success(passcode)
                } else {
                    PasscodeScreenEvent.NonEqualsPasscodes
                }
            }
    }
}
