package wtf.speech.feature.passcode.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

internal typealias PasscodeViewModel = BaseViewModel<
        PasscodeScreenState,
        PasscodeScreenAction,
        PasscodeScreenEvent,
        PasscodeScreenEffect
        >

internal abstract class BasePasscodeViewModel(
    state: PasscodeScreenState = PasscodeScreenState()
) : PasscodeViewModel(state) {

    fun addNumber(number: Int) = handleAction(PasscodeScreenAction.EnterNumber(number))

    fun backspace() = handleAction(PasscodeScreenAction.Backspace)

    fun clear() = handleAction(PasscodeScreenAction.Clear)

    override fun PasscodeScreenState.reduce(event: PasscodeScreenEvent) = when (event) {
        is PasscodeScreenEvent.EnterNumber -> enterNumber(event.number)
        PasscodeScreenEvent.DeleteLastPasscode -> this.deleteLast()

        PasscodeScreenEvent.ClearPasscode -> this.clear()

        else -> this
    }

    override suspend fun processAction(action: PasscodeScreenAction) = when (action) {
        PasscodeScreenAction.Backspace -> PasscodeScreenEvent.DeleteLastPasscode
        PasscodeScreenAction.EnableBiometricAuth -> PasscodeScreenEvent.StartBiometricAuthentication
        PasscodeScreenAction.Clear -> PasscodeScreenEvent.ClearPasscode
        is PasscodeScreenAction.EnterNumber -> PasscodeScreenEvent.EnterNumber(action.number)
    }

    override suspend fun handleEvent(event: PasscodeScreenEvent) = when (event) {
        is PasscodeScreenEvent.EnterNumber -> state.value.enteredPasscode.let { passcode ->
            if (isPasscodeFiled(passcode)) {
                return@let PasscodeScreenEffect.Success(EncryptionSecretKey(byteArrayOf()))
//                return@let checkPasscode(state.value.enteredPasscode)
            }

            null
        }

        PasscodeScreenEvent.StartBiometricAuthentication -> PasscodeScreenEffect.StartBiometricAuth
        else -> null
    }

    protected abstract suspend fun checkPasscode(passcode: List<Int>): PasscodeScreenEffect
    protected abstract fun PasscodeScreenState.onStartBiometricAuth(): PasscodeScreenState

    private fun PasscodeScreenState.enterNumber(number: Int): PasscodeScreenState {
        if (isPasscodeFiled(enteredPasscode)) return this

        val state = this.add(number)

        if (isPasscodeFiled(enteredPasscode)) {
            return state.loading()
        }

        return state
    }

    private fun isPasscodeFiled(passcode: SnapshotStateList<Int>) = passcode.size == PASSCODE_SIZE
}
