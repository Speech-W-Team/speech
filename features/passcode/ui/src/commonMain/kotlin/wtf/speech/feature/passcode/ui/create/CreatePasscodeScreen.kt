package wtf.speech.feature.passcode.ui.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.feature.passcode.ui.PasscodeContent
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.feature.passcode.ui.confirm.ConfirmPasscodeScreen

class CreatePasscodeScreen private constructor(
    private val viewModel: CreatePasscodeViewModel
) : Screen() {
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val state by viewModel.state.collectAsState()
        val effect by viewModel.effect.collectAsState(null)
        val passcode = state.enteredPasscode

        LaunchedEffect(effect) {
            when (effect) {
                is PasscodeScreenEffect.EnterPasscodeSuccess -> routeManager.navigateTo(
                    ConfirmPasscodeScreen.ID,
                    extras = ConfirmPasscodeScreen.ConfirmPasscodeExtra(passcode)
                )
                else -> Unit
            }
        }

        DisposableEffect(Unit) {
            onDispose(viewModel::clear)
        }

        PasscodeContent(
            onPasscodeEntered = viewModel::addNumber,
            title = "Create Passcode",
            onDeletePressed = viewModel::backspace,
            passcodeScreenState = state
        )
    }

    data class CreatePasscodeExtra(val encryptionKey: ByteArray): Extra {
        override val key: String
            get() = CREATE_PASSCODE_EXTRA_KEY
        override val data: Any
            get() = encryptionKey

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is CreatePasscodeExtra) return false

            if (!encryptionKey.contentEquals(other.encryptionKey)) return false

            return true
        }

        override fun hashCode(): Int {
            return encryptionKey.contentHashCode()
        }
    }

    companion object Builder : ScreenBuilder {

        const val CREATE_PASSCODE_EXTRA_KEY = "CREATE_PASSCODE_EXTRA_KEY"
        const val ID = "CreatePasscode"

        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            if (extra !is CreatePasscodeExtra) throw IllegalArgumentException("Illegal arguments passed: $extra")

            return CreatePasscodeScreen(CreatePasscodeViewModel(encryptionKey = extra.encryptionKey))
        }
    }
}
