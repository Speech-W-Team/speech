package wtf.speech.feature.passcode.ui.create

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

class CreatePasscodeScreen {

    @Composable
    fun Content() {
        val passcode = remember { mutableStateListOf<Int>() }

        val passcodeContentState: ContentState<Unit>? by remember(passcode.size) {
            mutableStateOf(
                when {
                    passcode.size < MAX_PASSCODE_SIZE -> null
                    passcode.size == MAX_PASSCODE_SIZE -> ContentState.Success(Unit)
                    else -> ContentState.Error(PasscodeScreenError.InvalidPasscode)
                }
            )
        }

        val passcodeScreenState by remember(passcode.size, passcodeContentState) {
            mutableStateOf(
                PasscodeScreenState(
                    passcode,
                    passcode.size - 1,
                    passcodeContentState
                )
            )
        }

        LaunchedEffect(passcodeContentState) {
            when (passcodeContentState) {
                is ContentState.Error<*, *> -> {
                    delay(ERROR_ANIMATION_DELAY)
                    passcode.clear()
                }

                is ContentState.Success -> delay(SUCCESS_ANIMATION_DELAY)
                else -> Unit
            }
        }

        PasscodeContent(
            onPasscodeEntered = passcode::add,
            title = "Create Passcode",
            onDeletePressed = passcode::removeLastOrNull,
            passcodeScreenState = passcodeScreenState
        )
    }

    companion object {
        private const val ERROR_ANIMATION_DELAY = 600L
        private const val SUCCESS_ANIMATION_DELAY = 600L
    }
}
