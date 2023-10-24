package wtf.speech.feature.passcode.ui.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.delay
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.ContentState
import wtf.speech.feature.passcode.ui.PasscodeContent
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.feature.passcode.ui.confirm.ConfirmPasscodeScreen

class CreatePasscodeScreen private constructor(private val viewModel: CreatePasscodeViewModel) : Screen() {
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current

        val state by viewModel.state.collectAsState()
        val effect by viewModel.effect.collectAsState(null)
        val passcode = state.enteredPasscode

        LaunchedEffect(state) {
            when (state.contentState) {
                is ContentState.Error<*, *> -> {
                    delay(ERROR_ANIMATION_DELAY)
                    passcode.clear()
                }

                is ContentState.Success -> delay(SUCCESS_ANIMATION_DELAY)
                else -> Unit
            }
        }

        LaunchedEffect(effect) {
            when (effect) {
                is PasscodeScreenEffect.AuthSuccess -> routeManager.navigateTo(
                    ConfirmPasscodeScreen.ID,
                    extras = ConfirmPasscodeScreen.ConfirmPasscodeExtra(passcode)
                )

                is PasscodeScreenEffect.StartBiometricAuth -> Unit
                null -> Unit
            }
        }

        PasscodeContent(
            onPasscodeEntered = passcode::add,
            title = "Create Passcode",
            onDeletePressed = passcode::removeLastOrNull,
            passcodeScreenState = state
        )
    }

    companion object Builder : ScreenBuilder {
        private const val ERROR_ANIMATION_DELAY = 600L
        private const val SUCCESS_ANIMATION_DELAY = 600L
        const val ID = "CreatePasscode"

        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return CreatePasscodeScreen(CreatePasscodeViewModel())
        }
    }
}
