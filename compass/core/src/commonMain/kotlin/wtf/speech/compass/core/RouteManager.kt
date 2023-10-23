package wtf.speech.compass.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

interface RouteManager {
    val activeGraph: MutableState<NavigationGraph?>
    val currentScreen: Screen?

    fun registerGraph(graph: NavigationGraph)

    /**
     * Switches to a different navigation graph and navigates to its initial screen.
     *
     * @param graphId The ID of the graph to switch to.
     * @return True if the switch was successful, false otherwise.
     */
    fun switchToGraph(graphId: String): Boolean
    fun navigateTo(screenId: String, params: Map<String, String>? = null, extras: Extra? = null): Boolean
    fun navigateBack(): Boolean
    fun closeActiveGraph(): Boolean
    fun handleDeepLink(deepLink: String): Boolean
}

class RouteManagerImpl(initialGraph: NavigationGraph) : RouteManager {
    private val graphs = mutableMapOf<String, NavigationGraph>().apply { put(initialGraph.id, initialGraph) }
    private val activeGraphStack = mutableListOf<NavigationGraph>().apply { add(initialGraph) }

    override val activeGraph: MutableState<NavigationGraph?> = mutableStateOf(graphs[initialGraph.id])

    override val currentScreen: Screen?
        get() = activeGraph.value?.currentScreen?.value?.screen

    override fun registerGraph(graph: NavigationGraph) {
        graphs[graph.id] = graph
    }

    /**
     * Switches to a different navigation graph and navigates to its initial screen.
     *
     * @param graphId The ID of the graph to switch to.
     * @return True if the switch was successful, false otherwise.
     */
    override fun switchToGraph(graphId: String): Boolean {
        val graph = graphs[graphId]
        if (graph != null) {
            activeGraphStack.add(graph)
            updateActiveGraph()
            return true
        }
        return false
    }

    override fun navigateTo(screenId: String, params: Map<String, String>?, extras: Extra?): Boolean {
        return activeGraph.value?.navigateTo(screenId, params, extras) ?: false
    }

    override fun navigateBack(): Boolean {
        val navigatedBack = activeGraph.value?.navigateBack() ?: false

        if (!navigatedBack) {
            return closeActiveGraph()
        }

        return navigatedBack
    }

    override fun closeActiveGraph(): Boolean {
        if (activeGraphStack.isNotEmpty()) {
            activeGraphStack.removeLast() // Remove current graph

            // Set the previous graph as the active graph
            activeGraph.value = if (activeGraphStack.isNotEmpty()) {
                activeGraphStack.last()
            } else {
                null
            }

            return true
        }
        return false
    }

    override fun handleDeepLink(deepLink: String): Boolean {
        val matchingScreenBuilder = graphs.values
            .flatMap { it.routes.values }
            .map { it }
            .filterIsInstance<DeepLinkScreenBuilder>()
            .firstOrNull { it.matches(deepLink) }

        matchingScreenBuilder?.let {
            val parameters = it.extractParameters(deepLink)
            // Handle the parameters as needed, e.g., pass them to the screen
            navigateTo(matchingScreenBuilder.id, parameters, null)
            return true
        }
        return false
    }

    private fun updateActiveGraph() {
        activeGraph.value = activeGraphStack.last()
    }
}
