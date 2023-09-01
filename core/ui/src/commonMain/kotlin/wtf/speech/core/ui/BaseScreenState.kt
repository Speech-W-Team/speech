package wtf.speech.core.ui

import androidx.compose.runtime.Stable


@Stable
abstract class ScreenState<E : ErrorState> {
    /**
     * A value represents loading state
     * @return true if the state is an loading state, false otherwise
     */
    abstract val isLoading: Boolean

    /**
     * A value of error state
     * @return null if state is not error and ErrorState instance if state is error
     */
    abstract val error: E?
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
