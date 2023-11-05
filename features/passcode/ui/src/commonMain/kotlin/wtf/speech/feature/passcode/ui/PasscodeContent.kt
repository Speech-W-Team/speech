package wtf.speech.feature.passcode.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.speech.core.design.texts.LabelLargeText
import wtf.speech.core.design.texts.TitleLargeText
import wtf.speech.core.ui.ContentState

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun PasscodeContent(
    onPasscodeEntered: (Int) -> Unit,
    onDeletePressed: () -> Unit,
    passcodeScreenState: PasscodeScreenState,
    title: String,
    onBackPressed: () -> Unit = {},
    onBiometricPressed: () -> Unit = {},
    onLogoutPressed: () -> Unit = {}
) {
    Column(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
    ) {
        if (passcodeScreenState.showBackButton) {
            IconButton(
                onBackPressed,
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(Modifier.height(if (passcodeScreenState.showBackButton) 44.dp else 122.dp))
        TitleLargeText(title, Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

        Spacer(Modifier.height(32.dp))
        PasscodeCircles(passcodeScreenState)

        Spacer(Modifier.height(122.dp))
        PasscodeInput(
            onPasscodeEntered,
            onBiometricPressed,
            onDeletePressed,
            passcodeScreenState.isBiometricAuthEnabled,
            passcodeScreenState.showBackspaceButton
        )

        if (passcodeScreenState.showLogoutButton) {
            TextButton(
                onLogoutPressed,
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painterResource("ic_logout_button.xml"), contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    LabelLargeText("Logout", fontSize = 14.sp)
                }
            }
        }
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
        modifier = Modifier.fillMaxWidth(),
        targetState = passcodeContentState,
        transitionSpec = {
            when (targetState) {
                is ContentState.Error<*, *> -> fadeIn() with fadeOut()
                is ContentState.Loading -> expandIn() with shrinkOut()
                is ContentState.Success -> fadeIn() with fadeOut()
                null -> fadeIn() with shrinkOut()
            }
        },
        contentAlignment = Alignment.Center,
    ) { contentState ->
        when (contentState) {
            is ContentState.Error<*, *> -> ErrorPasscodeCircleRow(
                passcodeSize,
                Modifier.height(32.dp).fillMaxWidth()
            )

            is ContentState.Loading -> CircularProgressIndicator(
                strokeWidth = 4.dp,
                modifier = Modifier.wrapContentWidth().size(32.dp)
            )

            is ContentState.Success -> SuccessPasscodeCircleRow(
                passcodeSize,
                Modifier.height(32.dp).fillMaxWidth()
            )

            null -> PasscodeCircleRow(
                passcodeScreenState,
                passcodeSize,
                passcodeScreenState.position,
                Modifier.height(32.dp).fillMaxWidth()
            )
        }
    }
}
