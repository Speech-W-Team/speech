package wtf.speech.feature.passcode.ui.confirm

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import wtf.speech.feature.passcode.ui.LocalPasscodeNavigator
import wtf.speech.feature.passcode.ui.PasscodeContent
import wtf.speech.feature.passcode.ui.PasscodeGraphs.ERROR_ANIMATION_DELAY
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.features.passcode.domain.usecase.provideCheckPasscodesAreEqualUseCase
import wtf.speech.features.passcode.domain.usecase.provideGenerateEncryptionKeyUseCase

class ConfirmPasscodeScreen private constructor(
    private val passcodeViewModel: ConfirmPasscodeViewModel
) : Screen(passcodeViewModel) {

    override val enterTransition: EnterTransition = slideInHorizontally(initialOffsetX = { it })
    override val exitTransition: ExitTransition = slideOutHorizontally(targetOffsetX = { -it })

    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val navigationMediator = LocalPasscodeNavigator.current
        val router = LocalRouteManager.current
        val state by passcodeViewModel.state.collectAsState()
        val effect by passcodeViewModel.effect.collectAsState(null)

        LaunchedEffect(state) {
            if (state.contentState is ContentState.Error<*, *>) {
                delay(ERROR_ANIMATION_DELAY)
                passcodeViewModel.clear()
            }
        }

        LaunchedEffect(effect) {
            when (val e = effect) {
                is PasscodeScreenEffect.Success -> navigationMediator.onEnterPasscodeSuccess(e.encryptionKey)
                PasscodeScreenEffect.Logout -> navigationMediator.onLogout()
                else -> Unit
            }
        }

        PasscodeContent(
            onPasscodeEntered = passcodeViewModel::addNumber,
            title = "Confirm Passcode",
            onDeletePressed = passcodeViewModel::backspace,
            onBackPressed = router::navigateBack,
            passcodeScreenState = state,
        )
    }

    data class ConfirmPasscodeExtra(val passcode: List<Int>) : Extra {
        override val key: String = KEY

        companion object {
            const val KEY = "passcode"
        }
    }

    companion object Builder : ScreenBuilder {
        const val ID = "ConfirmPasscode"
        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            require(extra is ConfirmPasscodeExtra) { "Illegal arguments passed: $extra" }
            val checkPasscodeUseCase = provideCheckPasscodesAreEqualUseCase()
            val generateEncryptionKeyUseCase = provideGenerateEncryptionKeyUseCase()

            return ConfirmPasscodeScreen(
                ConfirmPasscodeViewModel(extra, checkPasscodeUseCase, generateEncryptionKeyUseCase)
            )
        }
    }
}
