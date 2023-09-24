package wtf.speech.compass.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.staticCompositionLocalOf
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
public fun rememberRouter(getScreenById: (Screen.Id) -> Screen, initialScreen: Screen): Router {
    return rememberSaveable(
        saver = RouterSaver(getScreenById),
        init = { SimpleRouter(initialScreen) }
    )
}

private class RouterSaver(private val getScreenById: (Screen.Id) -> Screen) : Saver<Router, Any> {
    override fun restore(value: Any): Router {
        return SimpleRouter(getScreenById(Screen.Id(value as String)))
    }

    override fun SaverScope.save(value: Router): Any {
        return value.currentScreen.value.id.value
    }
}
