package wtf.speech.feature.passcode.ui.enter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.delay
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.ContentState
import wtf.speech.feature.passcode.ui.PasscodeContent
import wtf.speech.features.passcode.domain.usecase.CheckPasscodesEqualsUseCase

private const val PASSCODE_CLEAR_DELAY = 600L

class EnterPasscodeScreen private constructor(private val viewModel: EnterPasscodeViewModel) :
    Screen() {
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()
        val passcode = state.enteredPasscode

        LaunchedEffect(state) {
            if (state.contentState is ContentState.Error<*, *>) {
                delay(PASSCODE_CLEAR_DELAY)
                passcode.clear()
            }
        }

        Column {
            PasscodeContent(
                onPasscodeEntered = viewModel::addNumber,
                title = "Enter Passcode",
                onDeletePressed = viewModel::backspace,
                passcodeScreenState = state
            )
        }
    }

    public companion object Builder : ScreenBuilder {
        const val ID = "ConfirmPasscode"
        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            val checkPasscodeUseCase = CheckPasscodesEqualsUseCase()
            return EnterPasscodeScreen(EnterPasscodeViewModel(checkPasscodeUseCase))
        }
    }
}
