package wtf.speech.feature.passcode.ui.enter

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import wtf.speech.feature.passcode.ui.PasscodeGraphs.ERROR_ANIMATION_DELAY
import wtf.speech.features.passcode.domain.usecase.provideCheckPasscodesAreEqualUseCase

class EnterPasscodeScreen private constructor(
    private val passcodeViewModel: EnterPasscodeViewModel
) : Screen(passcodeViewModel) {

    override val enterTransition: EnterTransition = slideInVertically(initialOffsetY = { it })
    override val exitTransition: ExitTransition = slideOutVertically(targetOffsetY = { -it })

    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val state by passcodeViewModel.state.collectAsState()

        LaunchedEffect(state) {
            if (state.contentState is ContentState.Error<*, *>) {
                delay(ERROR_ANIMATION_DELAY)
                passcodeViewModel.clear()
            }
        }

        Column {
            PasscodeContent(
                onPasscodeEntered = passcodeViewModel::addNumber,
                title = "Enter Passcode",
                onDeletePressed = passcodeViewModel::backspace,
                passcodeScreenState = state
            )
        }
    }

    companion object Builder : ScreenBuilder {
        const val ID = "ConfirmPasscode"
        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            val checkPasscodeUseCase = provideCheckPasscodesAreEqualUseCase()
            return EnterPasscodeScreen(EnterPasscodeViewModel(checkPasscodeUseCase))
        }
    }
}
