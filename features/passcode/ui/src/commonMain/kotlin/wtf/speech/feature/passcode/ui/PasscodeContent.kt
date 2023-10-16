package wtf.speech.feature.passcode.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import wtf.speech.core.design.texts.TitleLargeText
import wtf.speech.core.ui.ContentState

@Composable
internal fun PasscodeContent(
    onPasscodeEntered: (Int) -> Unit,
    onDeletePressed: () -> Unit,
    passcodeScreenState: PasscodeScreenState,
    title: String,
    onBiometricPressed: () -> Unit = {},
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(104.dp))
        TitleLargeText(title, Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

        Spacer(Modifier.height(24.dp))
        PasscodeCircles(passcodeScreenState)

        PasscodeInput(
            onPasscodeEntered,
            onBiometricPressed,
            onDeletePressed,
            passcodeScreenState.isBiometricAuthEnabled,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PasscodeCircles(
    passcodeScreenState: PasscodeScreenState
) {
    val passcodeContentState = passcodeScreenState.contentState
    val passcodeSize = passcodeScreenState.maxPasscodeSize
    AnimatedContent(
        targetState = passcodeContentState,
        transitionSpec = {
            when (targetState) {
                is ContentState.Error<*, *> -> fadeIn() with fadeOut()
                is ContentState.Loading -> fadeIn() with fadeOut()
                is ContentState.Success -> fadeIn() with fadeOut()
                null -> fadeIn() with fadeOut()
            }
        },
        contentAlignment = Alignment.Center,
    ) { contentState ->
        when (contentState) {
            is ContentState.Error<*, *> -> ErrorPasscodeCircleRow(passcodeSize, Modifier.height(32.dp).fillMaxWidth())
            is ContentState.Loading -> CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(32.dp))
            is ContentState.Success -> SuccessPasscodeCircleRow(passcodeSize, Modifier.height(32.dp).fillMaxWidth())
            null -> PasscodeCircleRow(
                passcodeScreenState,
                passcodeSize,
                passcodeScreenState.position,
                Modifier.height(32.dp).fillMaxWidth()
            )
        }
    }
}
