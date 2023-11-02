package wtf.speech.feature.passcode.ui.confirm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.delay
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.ContentState
import wtf.speech.feature.passcode.ui.LocalPasscodeNavigator
import wtf.speech.feature.passcode.ui.PasscodeContent
import wtf.speech.feature.passcode.ui.PasscodeGraphs.ERROR_ANIMATION_DELAY
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.features.passcode.domain.usecase.CheckPasscodesEqualsUseCase

class ConfirmPasscodeScreen private constructor(
    private val viewModel: ConfirmPasscodeViewModel
) : Screen() {
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val navigationMediator = LocalPasscodeNavigator.current
        val state by viewModel.state.collectAsState()
        val effect by viewModel.effect.collectAsState(null)

        LaunchedEffect(state) {
            if (state.contentState is ContentState.Error<*, *>) {
                delay(ERROR_ANIMATION_DELAY)
                viewModel.clear()
            }
        }

        LaunchedEffect(effect) {
            when (val e = effect) {
                is PasscodeScreenEffect.EnterPasscodeSuccess -> navigationMediator.onEnterPasscodeSuccess(e.encryptionKey)
                PasscodeScreenEffect.Logout -> navigationMediator.onLogout()
                else -> Unit
            }
        }

        PasscodeContent(
            onPasscodeEntered = viewModel::addNumber,
            title = "Confirm Passcode",
            onDeletePressed = viewModel::backspace,
            passcodeScreenState = state
        )
    }

    data class ConfirmPasscodeExtra(val passcode: List<Int>) : Extra {
        override val key: String = KEY
        override val data: Any = passcode

        companion object {
            const val KEY = "passcode"
        }
    }

    companion object Builder : ScreenBuilder {
        const val ID = "ConfirmPasscode"
        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            val passcode = (extra as? ConfirmPasscodeExtra) ?: throw IllegalArgumentException("Illegal arguments passed: $extra")
            val checkPasscodeUseCase = CheckPasscodesEqualsUseCase()

            return ConfirmPasscodeScreen(ConfirmPasscodeViewModel(passcode, checkPasscodeUseCase))
        }
    }
}
