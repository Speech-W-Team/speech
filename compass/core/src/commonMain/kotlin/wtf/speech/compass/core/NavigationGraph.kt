package wtf.speech.compass.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

internal data class NavigationEntry(val screen: Screen, val params: Map<String, String>?, val extras: Extra?)

/**
 * Represents a navigation graph with possible navigation paths, initial screen, and parameters.
 *
 * @property paths Mapping of each screen to its possible destinations.
 * @property initialScreenId The ID of the initial screen for this graph.
 * @property parameters Optional parameters associated with the graph.
 */
data class NavigationGraph(
    val id: String,
    val initialScreen: ScreenBuilder,
    val extra: Extra? = null,
    val parameters: Map<String, String> = emptyMap(),
    private val paths: Map<String, Set<String>>
) {
    internal val routes = mutableMapOf<String, ScreenBuilder>()
    private val backStack = mutableListOf(NavigationEntry(initialScreen.build(parameters, extra), parameters, extra))

    internal var currentScreen: MutableState<NavigationEntry> = mutableStateOf(backStack.last())
        private set

    fun register(route: Route) {
        routes[route.id] = route.screenBuilder
    }

    fun navigateTo(screenId: String, params: Map<String, String>?, extras: Extra? ): Boolean {
        val screenBuilder = routes[screenId]
        if (screenBuilder != null && canNavigateTo(screenId)) {
            backStack.add(NavigationEntry(screenBuilder.build(params, extra), params, extras))
            updateCurrentScreen()
            return true
        }
        return false
    }

    fun navigateBack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeLast()
            currentScreen.value = backStack.last()
            return true
        }
        return false
    }

    private fun canNavigateTo(screenId: String): Boolean {
        val currentId = currentScreen.value.screen.id
        return paths[currentId]?.contains(screenId) ?: true
    }

    private fun updateCurrentScreen() {
        currentScreen.value = backStack.last()
    }
}
