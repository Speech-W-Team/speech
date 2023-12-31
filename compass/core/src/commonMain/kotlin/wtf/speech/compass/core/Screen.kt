package wtf.speech.compass.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

abstract class Screen {
    open val enterTransition: EnterTransition = fadeIn()
    open val exitTransition: ExitTransition = fadeOut()

    abstract val id: String

    @Composable
    abstract fun Content()
}
