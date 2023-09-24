package wtf.speech.compass.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import wtf.speech.compass.core.graph.Screen

@Suppress("TooManyFunctions", "ComplexInterface")
interface Router {
    val currentScreen: State<Screen>

    fun replaceRoot(screen: Screen)

    fun replaceRoot(screenId: Screen.Id)

    fun push(screen: Screen)

    fun pop()

    fun popTo(screen: Screen, inclusive: Boolean = false)

    fun popTo(id: Screen.Id, inclusive: Boolean = false)

    fun replace(screen: Screen)

    fun clear()

    fun onBackPressed()
}

@Suppress("TooManyFunctions")
@Serializable
public class SimpleRouter internal constructor(private val initialScreen: Screen) : Router {
    private val currentBackstackId: MutableState<Screen.Id> = mutableStateOf(initialScreen.id)

    @Contextual
    private val backstacks: SnapshotStateMap<Screen.Id, Backstack> = mutableStateMapOf(
        currentBackstackId.value to DefaultBackstack(initialScreen)
    )

    private var currentBackstack: Backstack by mutableStateOf(
        backstacks[currentBackstackId.value] ?: DefaultBackstack(initialScreen)
    )

    override val currentScreen: State<Screen> = derivedStateOf { currentBackstack.currentScreen.value }

    override fun replaceRoot(screen: Screen) {
        val backstack = backstacks.getOrPut(screen.id) { DefaultBackstack(screen) }

        if (currentBackstackId.value == screen.id) backstack.removeUntilRoot()

        updateCurrentBackstack(screen.id)
    }

    override fun replaceRoot(screenId: Screen.Id) {
        if (!backstacks.contains(screenId)) return

        updateCurrentBackstack(screenId)
    }

    override fun push(screen: Screen) {
        currentBackstack.addLast(screen)
        updateCurrentBackstack(currentBackstackId.value)
    }

    override fun pop() {
        currentBackstack.removeLast()
    }

    override fun popTo(screen: Screen, inclusive: Boolean) {
        currentBackstack.removeLastUntil(screen, inclusive)
    }

    override fun popTo(id: Screen.Id, inclusive: Boolean) {
        currentBackstack.removeLastUntil(id, inclusive)
    }

    override fun replace(screen: Screen) {
        pop()
        push(screen)
    }

    override fun clear() = currentBackstack.removeUntilRoot()

    override fun onBackPressed() = pop()

    private fun updateCurrentBackstack(screenId: Screen.Id) {
        currentBackstackId.value = screenId
        currentBackstack = backstacks[screenId] ?: DefaultBackstack(initialScreen)
    }

    public companion object {
        fun new(initialScreen: Screen): SimpleRouter {
            return SimpleRouter(initialScreen = initialScreen)
        }
    }
}
