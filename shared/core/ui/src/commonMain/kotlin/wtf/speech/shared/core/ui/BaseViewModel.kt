package wtf.speech.shared.core.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * An abstract class for the base ViewModel, using the MVI (Model-View-Intent) pattern.
 * @param S the type of the screen state.
 * @param A the type of the user action on the screen.
 * @param E the type of the event on the screen.
 * @param F the type of the effect on the screen.
 */
abstract class BaseViewModel<E : ErrorState, S : ScreenState<E>, A : ScreenAction, V : ScreenEvent, F : ScreenEffect>(initialState: S) {
    /**
     * A MutableStateFlow for storing the screen state.
     */
    private val _state = MutableStateFlow(initialState)

    /**
     * A property for storing the screen state.
     */
    val state: StateFlow<S> = _state

    /**
     * A Channel for sending effects to the screen.
     */
    private val _effect = MutableSharedFlow<F>()

    /**
     * A property for sending effects to the screen.
     */
    val effect: Flow<F> = _effect

    /**
     * A CoroutineScope for launching coroutines.
     */
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /**
     * A function for handling user actions on the screen.
     * @param action a user action on the screen.
     */
    fun handleAction(action: A) = viewModelScope.launch {
        val event = this@BaseViewModel.processAction(action)
        val newState = state.value.reduce(event)
        _state.value = newState
        val effect = handleEvent(event)
        if (effect != null) {
            _effect.emit(effect)
        }
    }

    /**
     * An abstract function for applying an event to the state and getting a new state.
     * @param event an event on the screen.
     * @return a new screen state.
     */
    protected abstract fun S.reduce(event: V): S

    /**
     * An abstract function for processing the user action and getting an event.
     * @param action a user action on the screen.
     * @return an event on the screen.
     */
    protected abstract suspend fun processAction(action: A): V

    /**
     * An abstract function for handling the event and getting an effect if any.
     * @param event an event on the screen.
     * @return an effect on the screen or null if there is no effect.
     */
    protected abstract fun handleEvent(event: V): F?
}