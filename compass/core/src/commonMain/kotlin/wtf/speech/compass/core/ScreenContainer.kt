package wtf.speech.compass.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import wtf.speech.compass.core.graph.Screen

@Composable
public fun ScreenContainer(router: Router) {
    CompositionLocalProvider(LocalRouter provides router) {
        val currentScreen by router.currentScreen

        currentScreen.Content()
    }
}

val LocalRouter = staticCompositionLocalOf<Router> { error("Router not provided") }

@Composable
public fun rememberRouter(initialScreen: Screen, coroutineScope: CoroutineScope = rememberCoroutineScope()): Router {
    val router = remember(coroutineScope) {
        SimpleRouter.new(initialScreen)
    }

    DisposableEffect(router) {
        onDispose {
            coroutineScope.launch { router.clear() }
        }
    }

    return router
}
