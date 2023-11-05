package wtf.speech.feature.passcode.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.speech.core.design.texts.HeadlineSmallText

private const val PASSCODE_INPUT_COLUMNS = 3
private const val PASSCODE_INPUT_ROWS = 3

@Composable
internal fun PasscodeInput(
    onPasscodeEntered: (Int) -> Unit,
    onBiometricPressed: () -> Unit,
    onDeletePressed: () -> Unit,
    isBiometricEnabled: Boolean,
    showBackspaceButton: Boolean,
    backgroundColor: Color = MaterialTheme.colorScheme.surface
) {
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .background(backgroundColor, RoundedCornerShape(24.dp))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        var position = remember { 0 }
        repeat(PASSCODE_INPUT_ROWS) { row ->
            if (row == 0) Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(PASSCODE_INPUT_COLUMNS) { column ->
                    position++

                    val item = remember((row + 1) * (column + 1)) { position }
                    Spacer(Modifier.width(if (column > 0) 48.dp else 16.dp))
                    PasscodeNumberButton(
                        onPasscodeEntered,
                        item,
                        Modifier.size(56.dp)
                    )

                    if (column == PASSCODE_INPUT_COLUMNS - 1) Spacer(Modifier.width(16.dp))
                }
            }

            Spacer(Modifier.height(24.dp))
        }
        BottomPasscodeNumPadRow(
            isBiometricEnabled,
            onBiometricPressed,
            onPasscodeEntered,
            onDeletePressed,
            showBackspaceButton
        )
        Spacer(Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BottomPasscodeNumPadRow(
    isBiometricEnabled: Boolean,
    onBiometricPressed: () -> Unit,
    onPasscodeEntered: (Int) -> Unit,
    onDeletePressed: () -> Unit,
    showBackspaceButton: Boolean
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isBiometricEnabled) {
            BiometricAuthButton(onBiometricPressed, Modifier.size(56.dp))
        } else {
            Spacer(Modifier.size(56.dp))
        }
        Spacer(Modifier.width(48.dp))
        PasscodeNumberButton(onPasscodeEntered, 0, Modifier.size(56.dp))
        Spacer(Modifier.width(48.dp))

        AnimatedContent(
            showBackspaceButton, Modifier.size(56.dp),
            contentAlignment = Alignment.Center,
            transitionSpec = { fadeIn() with fadeOut() }
        ) {
            if (showBackspaceButton) {
                PasscodeDeleteButton(onDeletePressed, Modifier)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PasscodeDeleteButton(onDeletePressed: () -> Unit, modifier: Modifier = Modifier) {
    PasscodeIconButton(onDeletePressed, painterResource("ic_delete.xml"), modifier)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BiometricAuthButton(onBiometricPressed: () -> Unit, modifier: Modifier = Modifier) {
    PasscodeIconButton(onBiometricPressed, painterResource("ic_fingerprint.xml"), modifier)
}

@Composable
private fun PasscodeIconButton(onClick: () -> Unit, icon: Painter, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier.size(56.dp)) {
        Icon(
            icon,
            contentDescription = null,
            Modifier.height(32.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PasscodeNumberButton(
    onPasscodeEntered: (Int) -> Unit,
    number: Int,
    modifier: Modifier = Modifier
) {
    TextButton(onClick = { onPasscodeEntered(number) }, modifier) {
        HeadlineSmallText(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = number.toString(),
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight(400),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
