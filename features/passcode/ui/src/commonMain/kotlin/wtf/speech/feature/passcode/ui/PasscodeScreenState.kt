package wtf.speech.feature.passcode.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ErrorState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

const val PASSCODE_SIZE = 6

internal data class PasscodeScreenState(
    val enteredPasscode: SnapshotStateList<Int> = mutableStateListOf(),
    val position: Int = enteredPasscode.size - 1,
    val contentState: ContentState<Unit>? = null,
    val maxPasscodeSize: Int = PASSCODE_SIZE,
    val showBackspaceButton: Boolean = position > -1,
    val isBiometricAuthEnabled: Boolean = false,
    val showLogoutButton: Boolean = true,
    val showBackButton: Boolean = false,
) : ScreenState {

    fun add(number: Int): PasscodeScreenState {
        enteredPasscode.add(number)
        return updateLastPosition()
    }

    fun deleteLast(): PasscodeScreenState {
        if (enteredPasscode.isEmpty()) return this

        enteredPasscode.removeLast()
        return updateLastPosition()
    }

    fun clear(): PasscodeScreenState {
        if (enteredPasscode.isEmpty()) return this

        enteredPasscode.clear()
        return updateLastPosition()
    }

    fun loading(): PasscodeScreenState = copy(
        contentState = ContentState.Loading(),
        showBackspaceButton = false
    )

    fun error(error: PasscodeScreenError): PasscodeScreenState {
        return copy(contentState = ContentState.Error(error))
    }

    private fun updateLastPosition() = copy(
        position = enteredPasscode.size - 1,
        showBackspaceButton = enteredPasscode.size - 1 > -1,
        contentState = null
    )
}

internal sealed class PasscodeScreenEvent : ScreenEvent {
    data class EnterNumber(val number: Int) : PasscodeScreenEvent()
    data object InvalidPasscode : PasscodeScreenEvent()
    data object StartBiometricAuthentication : PasscodeScreenEvent()
    data object ClearPasscode : PasscodeScreenEvent()
    data object DeleteLastPasscode : PasscodeScreenEvent()
}

internal sealed class PasscodeScreenAction : ScreenAction {
    data class EnterNumber(val number: Int) : PasscodeScreenAction()
    data object Clear : PasscodeScreenAction()
    data object Backspace : PasscodeScreenAction()
    data object EnableBiometricAuth : PasscodeScreenAction()
}

internal sealed class PasscodeScreenEffect : ScreenEffect {
    data class Success(val encryptionKey: EncryptionSecretKey) : PasscodeScreenEffect()
    data object StartBiometricAuth : PasscodeScreenEffect()
    data object Logout : PasscodeScreenEffect()
    data object InvalidPasscode : PasscodeScreenEffect()
}

sealed class PasscodeScreenError(error: Throwable) : ErrorState(error) {
    data object InvalidPasscode : PasscodeScreenError(Throwable("Passcode error"))
    data object BiometricAuthError : PasscodeScreenError(Throwable("Biometric auth error"))

    data object TokenError : PasscodeScreenError(Throwable("Token error"))
}
