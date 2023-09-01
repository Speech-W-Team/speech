package wtf.speech.core.ui

import androidx.compose.runtime.Composable

@Composable
inline fun <S : ScreenState<E>, E : ErrorState, F : ScreenEffect> BaseScreenContainer(
    onScreenState: @Composable (S) -> Unit,
    onErrorScreenState: @Composable (E) -> Unit,
    noinline onEffect: @Composable (F) -> Unit,
    state: S,
    effect: F?,
) {
    HandleState(state, onErrorScreenState, onScreenState)

    HandleEffect(onEffect, effect)
}

@Composable
fun <F : ScreenEffect> HandleEffect(onEffect: @Composable (F) -> Unit, effect: F?) {
    if (effect != null) onEffect(effect)
}

@Composable
inline fun <E : ErrorState, S : ScreenState<E>> HandleState(
    state: S,
    onErrorScreenState: @Composable (E) -> Unit,
    onScreenState: @Composable (S) -> Unit
) {
    when (val errorState = state.error) {
        null -> onScreenState(state)
        else -> onErrorScreenState(errorState)
    }
}