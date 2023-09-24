package wtf.speech.compass.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import wtf.speech.compass.core.graph.Screen

@Serializable
public sealed class BackStackEvent {
    data class ScreenPushed(val screen: Screen) : BackStackEvent()
    data class ScreenPopped(val screen: Screen) : BackStackEvent()
}

public interface Backstack {

    val currentScreen: State<Screen>
    val parentScreen: Screen

    val canPop: Boolean

    val backStackEvents: SharedFlow<BackStackEvent>

    fun addLast(screen: Screen)
    fun removeLast()
    fun removeLastUntil(screen: Screen, inclusive: Boolean = false)
    fun removeLastUntil(screenId: Screen.Id, inclusive: Boolean = false)

    fun removeUntilRoot()
}

@Serializable
internal class DefaultBackstack(private val initialScreen: Screen) : Backstack {

    @Contextual
    private val screens: ArrayDeque<Screen> = ArrayDeque(listOf(initialScreen))
    private var _currentScreen: MutableState<Screen> = mutableStateOf(initialScreen)
    override val currentScreen: State<Screen> = _currentScreen

    private val _backStackEvents = MutableSharedFlow<BackStackEvent>()
    override val backStackEvents: SharedFlow<BackStackEvent> = _backStackEvents

    override val canPop: Boolean
        get() = screens.size > 1

    override val parentScreen: Screen
        get() = screens.first()

    override fun addLast(screen: Screen) {
        screens.addLast(screen)
        updateLastScreen()
        _backStackEvents.tryEmit(BackStackEvent.ScreenPushed(screen))
    }

    override fun removeLast() {
        if (!canPop) return
        screens.removeLast()
        updateLastScreen()
        _backStackEvents.tryEmit(BackStackEvent.ScreenPopped(currentScreen.value))
    }

    override fun removeLastUntil(screen: Screen, inclusive: Boolean) {
        if (!canPop) return

        val index = screens.indexOf(screen)
        val position = if (inclusive) index - 1 else index
        val removedScreens = screens.subList(position, screens.size)
        screens.removeAll(removedScreens)
        updateLastScreen()
        _backStackEvents.tryEmit(BackStackEvent.ScreenPopped(currentScreen.value))
    }

    override fun removeLastUntil(screenId: Screen.Id, inclusive: Boolean) {
        if (!canPop) return

        val screen = screens.findLast { it.id == screenId }
        if (screen != null) removeLastUntil(screen, inclusive)
        _backStackEvents.tryEmit(BackStackEvent.ScreenPopped(currentScreen.value))
    }

    override fun removeUntilRoot() {
        if (!canPop) {
            updateLastScreen()
            return
        }

        val rootScreen = screens[0]
        screens.clear()
        screens.add(rootScreen)
        updateLastScreen()
        _backStackEvents.tryEmit(BackStackEvent.ScreenPopped(currentScreen.value))
    }

    private fun updateLastScreen() {
        _currentScreen.value = screens.last()
    }
}
