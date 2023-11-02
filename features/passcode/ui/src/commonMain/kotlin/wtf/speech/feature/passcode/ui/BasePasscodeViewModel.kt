package wtf.speech.feature.passcode.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.core.ui.ContentState

internal typealias PasscodeViewModel = BaseViewModel<PasscodeScreenError, PasscodeScreenState, PasscodeScreenAction, PasscodeScreenEvent, PasscodeScreenEffect>

internal abstract class BasePasscodeViewModel(
    state: PasscodeScreenState = PasscodeScreenState()
) : PasscodeViewModel(state) {

    private val enteredPasscode: SnapshotStateList<Int>
        get() = state.value.enteredPasscode

    fun addNumber(number: Int) = handleAction(PasscodeScreenAction.EnterNumber(number))

    fun backspace() = handleAction(PasscodeScreenAction.Backspace)

    fun clear() = handleAction(PasscodeScreenAction.Clear)

    override fun PasscodeScreenState.reduce(event: PasscodeScreenEvent) = when (event) {
        is PasscodeScreenEvent.EnterNumber -> this.apply { enteredPasscode.add(event.number) }
        PasscodeScreenEvent.DeleteLastPasscode -> copy(enteredPasscode = enteredPasscode.apply { removeLastOrNull() })
        PasscodeScreenEvent.NonEqualsPasscodes -> copy(contentState = ContentState.Error(PasscodeScreenError.InvalidPasscode))
        PasscodeScreenEvent.StartBiometricAuthentication -> onStartBiometricAuth()
        PasscodeScreenEvent.ClearPasscode -> copy(enteredPasscode = enteredPasscode.apply { clear() })
    }

    override suspend fun processAction(action: PasscodeScreenAction) = when (action) {
        PasscodeScreenAction.Backspace -> PasscodeScreenEvent.DeleteLastPasscode
        PasscodeScreenAction.EnableBiometricAuth -> PasscodeScreenEvent.StartBiometricAuthentication
        PasscodeScreenAction.Clear -> PasscodeScreenEvent.ClearPasscode
        is PasscodeScreenAction.EnterNumber -> enterNumber(action.number)
    }

    override fun handleEvent(event: PasscodeScreenEvent) = when (event) {
        is PasscodeScreenEvent.EnterNumber -> checkPasscode(enteredPasscode)
        PasscodeScreenEvent.StartBiometricAuthentication -> PasscodeScreenEffect.StartBiometricAuth
        else -> null
    }

    protected abstract fun checkPasscode(passcode: List<Int>): PasscodeScreenEffect?
    protected abstract fun PasscodeScreenState.onSuccess(passcode: List<Int>): PasscodeScreenState
    protected abstract fun PasscodeScreenState.onStartBiometricAuth(): PasscodeScreenState

    private fun enterNumber(number: Int): PasscodeScreenEvent {
        return  PasscodeScreenEvent.EnterNumber(number)
    }
}
