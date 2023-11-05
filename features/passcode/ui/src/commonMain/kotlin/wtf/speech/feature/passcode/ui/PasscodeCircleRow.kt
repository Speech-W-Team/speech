package wtf.speech.feature.passcode.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val CIRCLE_ANIMATION_LENGTH = 200L

@Composable
internal fun PasscodeCircleRow(
    passcodeScreenState: PasscodeScreenState,
    passcodeSize: Int,
    lastPosition: Int,
    modifier: Modifier = Modifier
) {
    PasscodeCircleRow(passcodeSize, modifier) { currentPosition ->
        PasscodeCircle(
            isFilled = passcodeScreenState.enteredPasscode.size > currentPosition,
            position = currentPosition,
            lastPosition = lastPosition
        )
    }
}

@Composable
internal fun PasscodeCircleRow(
    passcodeSize: Int,
    modifier: Modifier = Modifier,
    circleContent: @Composable (Int) -> Unit
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(passcodeSize) { position ->
            key(position) {
                circleContent(position)
                if (position < passcodeSize - 1) Spacer(Modifier.size(12.dp))
            }
        }
    }
}

@Composable
internal fun SuccessPasscodeCircleRow(passcodeSize: Int, modifier: Modifier = Modifier) {
    PasscodeCircleRow(passcodeSize, modifier) {
        SuccessPasscodeCircle()
    }
}

@Composable
internal fun ErrorPasscodeCircleRow(passcodeSize: Int, modifier: Modifier = Modifier) {
    PasscodeCircleRow(passcodeSize, modifier) {
        ErrorPasscodeCircle()
    }
}

@Composable
fun PasscodeCircle(isFilled: Boolean, position: Int, lastPosition: Int) {
    var shouldShowAnimation by remember(isFilled, position) {
        mutableStateOf(shouldChangeSize(isFilled, position, lastPosition))
    }
    val size by animateDpAsState(targetValue = if (shouldShowAnimation) 16.dp else 12.dp)

    LaunchedEffect(shouldShowAnimation) {
        delay(CIRCLE_ANIMATION_LENGTH)
        shouldShowAnimation = false
    }

    PasscodeCircle(
        if (isFilled) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        size
    )
}

@Composable
fun ErrorPasscodeCircle() {
    PasscodeCircle(MaterialTheme.colorScheme.error, 16.dp)
}

@Composable
fun SuccessPasscodeCircle() {
    PasscodeCircle(MaterialTheme.colorScheme.primary, 16.dp)
}

@Composable
fun PasscodeCircle(color: Color, size: Dp) {
    Box(
        Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

private val shouldChangeSizeCache = mutableMapOf<Pair<Boolean, Int>, Boolean>()

private fun shouldChangeSize(isFilled: Boolean, itemPosition: Int, lastPosition: Int): Boolean {
    println("shouldChangeSize: $isFilled, $itemPosition, $lastPosition")
    return shouldChangeSizeCache.getOrPut(Pair(isFilled, itemPosition)) { itemPosition == lastPosition && isFilled }
}
