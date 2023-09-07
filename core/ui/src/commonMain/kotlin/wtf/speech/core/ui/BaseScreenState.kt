package wtf.speech.core.ui

import androidx.compose.runtime.Stable

/**
 * A class represents content state
 */
@Stable
sealed class ContentState<T> {
    abstract class Success<T>(val item: T) : ContentState<T>()
    abstract class Error<E: ErrorState>(val error: E) : ContentState<Unit>()
    data object Loading : ContentState<Unit>()
}

/**
 * A base class for representing the states of a screen in MVI architecture
 */
@Stable
interface ScreenState {
    abstract class Content : ScreenState

    abstract class Error<E: ErrorState>(open val error: E) : ScreenState

    data object Loading : ScreenState
}

/**
 * A subclass of ScreenState that represents an error state
 */
@Stable
abstract class ErrorState(val error: Throwable) {

    /**
     * A value to get the error message
     * @return the error message or "Unknown error" if the message is null
     */
    val message = error.message ?: "Unknown error"

}

/**
 * A base class for representing the events that occur on a screen in MVI architecture
 */
interface ScreenEvent

/**
 * A base class for representing the actions that are performed on a screen in MVI architecture
 */
interface ScreenAction

/**
 *  A base class for representing the effects that are triggered on a screen in MVI architecture
 */
interface ScreenEffect
