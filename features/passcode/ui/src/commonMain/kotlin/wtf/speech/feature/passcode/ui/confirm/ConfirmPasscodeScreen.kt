package wtf.speech.feature.passcode.ui.confirm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import wtf.speech.core.ui.ContentState
import wtf.speech.feature.passcode.ui.MAX_PASSCODE_SIZE
import wtf.speech.feature.passcode.ui.PasscodeContent
import wtf.speech.feature.passcode.ui.PasscodeScreenError
import wtf.speech.feature.passcode.ui.PasscodeScreenState

object ConfirmPasscodeScreen {
    @Composable
    fun Content() {
        val passcode = remember { mutableStateListOf<Int>() }

        val passcodeContentState by remember(passcode.size) {
            mutableStateOf(
                when {
                    passcode.size < MAX_PASSCODE_SIZE -> null
                    passcode.size == MAX_PASSCODE_SIZE -> ContentState.Success(Unit)

                    else -> ContentState.Error(PasscodeScreenError.InvalidPasscode)
                }
            )
        }

        val passcodeScreenState by remember(passcode.size, passcodeContentState) {
            mutableStateOf(PasscodeScreenState(passcode, passcode.size - 1, passcodeContentState))
        }

        LaunchedEffect(passcodeContentState) {
            if (passcodeContentState is ContentState.Error<*, *>) {
                delay(600L)
                passcode.clear()
            }
        }

        PasscodeContent(
            onPasscodeEntered = passcode::add,
            title = "Create Passcode",
            onDeletePressed = passcode::removeLastOrNull,
            passcodeScreenState = passcodeScreenState
        )
    }

    public data class Extra(val passcode: List<Int>)
}
