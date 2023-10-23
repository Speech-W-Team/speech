@file:Suppress("UNCHECKED_CAST")

package wtf.speech.core.ui

import androidx.compose.runtime.Composable

@Composable
inline fun <S : ScreenState.Content, E : ErrorState, F : ScreenEffect> BaseScreenContainer(
    onScreenState: @Composable (S) -> Unit,
    onLoadingScreenState: @Composable () -> Unit,
    onErrorScreenState: @Composable (E) -> Unit,
    noinline onEffect: @Composable (F) -> Unit,
    state: S,
    effect: F?,
) {
    HandleState(onLoadingScreenState, onErrorScreenState, onScreenState, state)

    HandleEffect(onEffect, effect)
}

@Composable
fun <F : ScreenEffect> HandleEffect(onEffect: @Composable (F) -> Unit, effect: F?) {
    if (effect != null) onEffect(effect)
}

@Composable
inline fun <E : ErrorState, S : ScreenState> HandleState(
    onLoadingScreenState: @Composable () -> Unit,
    onErrorScreenState: @Composable (E) -> Unit,
    onScreenState: @Composable (S) -> Unit,
    state: S,
) {
    when (state) {
        is ScreenState.Content -> onScreenState(state)
        is ScreenState.Loading -> onLoadingScreenState()
        is ScreenState.Error<*> -> onErrorScreenState(state.error as E)
    }
}
