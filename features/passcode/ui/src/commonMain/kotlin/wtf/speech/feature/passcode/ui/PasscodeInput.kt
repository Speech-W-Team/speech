package wtf.speech.feature.passcode.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
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
    backgroundColor: Color = MaterialTheme.colorScheme.surface
) {
    var position = remember { 0 }
    Column(
        Modifier
            .padding(top = 104.dp, bottom = 28.dp)
            .padding(horizontal = 16.dp)
            .background(backgroundColor, RoundedCornerShape(24.dp))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        repeat(PASSCODE_INPUT_ROWS) { row ->
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(PASSCODE_INPUT_COLUMNS) { column ->
                    position++

                    val item = remember((row + 1) * (column + 1)) { position }
                    if (column > 0) Spacer(Modifier.width(48.dp))
                    PasscodeNumberButton(onPasscodeEntered, item, Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(24.dp))
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isBiometricEnabled) {
                BiometricAuthButton(onBiometricPressed, Modifier.weight(1f))
            } else {
                Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.width(48.dp))
            PasscodeNumberButton(onPasscodeEntered, 0, Modifier.weight(1f))
            Spacer(Modifier.width(48.dp))
            PasscodeDeleteButton(onDeletePressed, Modifier.weight(1f))
        }
        Spacer(Modifier.height(16.dp))
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
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            icon,
            contentDescription = null,
            Modifier.height(32.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PasscodeNumberButton(onPasscodeEntered: (Int) -> Unit, number: Int, modifier: Modifier = Modifier) {
    TextButton(onClick = { onPasscodeEntered(number) }, modifier) {
        HeadlineSmallText(
            modifier = Modifier.padding(horizontal = 21.dp, vertical = 12.dp),
            text = number.toString(),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
