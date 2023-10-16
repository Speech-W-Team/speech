package wtf.speech.feature.passcode.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import wtf.speech.core.ui.BaseViewModel

internal typealias PasscodeViewModel = BaseViewModel<PasscodeScreenError, PasscodeScreenState, PasscodeScreenAction, PasscodeScreenEvent, PasscodeScreenEffect>

internal open class BasePasscodeViewModel(
    state: PasscodeScreenState = PasscodeScreenState()
) : PasscodeViewModel(state) {

    private val enteredPasscode: SnapshotStateList<Int>
        get() = state.value.enteredPasscode

    override fun PasscodeScreenState.reduce(event: PasscodeScreenEvent): PasscodeScreenState {
        return when (event) {
            is PasscodeScreenEvent.EnterNumber -> copy(enteredPasscode = enteredPasscode.apply { add(event.number) })
            PasscodeScreenEvent.DeletePasscode -> copy(enteredPasscode = enteredPasscode.apply { removeLastOrNull() })
            PasscodeScreenEvent.NonEqualsPasscodes -> this
            PasscodeScreenEvent.StartBiometricAuthentication -> this
            is PasscodeScreenEvent.Success -> this
        }
    }

    override suspend fun processAction(action: PasscodeScreenAction): PasscodeScreenEvent {
        return when (action) {
            PasscodeScreenAction.DeletePasscode -> PasscodeScreenEvent.DeletePasscode
            PasscodeScreenAction.EnableBiometricAuth -> PasscodeScreenEvent.StartBiometricAuthentication
            is PasscodeScreenAction.EnterNumber -> enterNumber(action.number)
        }
    }

    override fun handleEvent(event: PasscodeScreenEvent): PasscodeScreenEffect? {
        return when (event) {
            is PasscodeScreenEvent.Success -> PasscodeScreenEffect.AuthSuccess()
            PasscodeScreenEvent.StartBiometricAuthentication -> PasscodeScreenEffect.StartBiometricAuth()
            else -> null
        }
    }

    protected open fun checkPasscode(passcode: List<Int>): PasscodeScreenEvent {
        return PasscodeScreenEvent.Success(passcode)
    }

    private fun enterNumber(number: Int): PasscodeScreenEvent {
        val passcode = enteredPasscode
        return when {
            passcode.size < MAX_PASSCODE_SIZE -> PasscodeScreenEvent.EnterNumber(number)
            passcode.size == MAX_PASSCODE_SIZE -> checkPasscode(passcode)
            else -> PasscodeScreenEvent.EnterNumber(number)
        }
    }
}
