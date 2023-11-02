package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue

/**
 * A composable that observes changes to the current screen in [RouteManagerImpl] and displays its content.
 *
 * @param routeManager The [RouteManagerImpl] responsible for managing navigation.
 */
@ExperimentalAnimationApi
@Composable
fun NavigationHost(routeManager: RouteManager, vararg values: ProvidedValue<*>) {
    CompositionLocalProvider(LocalRouteManager provides routeManager, *values) {
        val currentScreen: Screen? = routeManager.currentScreen

        AnimatedContent(
            currentScreen,
            transitionSpec = {
                val target = targetState ?: return@AnimatedContent fadeIn(
                    animationSpec = tween(
                        durationMillis = 220,
                        delayMillis = 90
                    )
                ) + scaleIn(
                    initialScale = 0.92f,
                    animationSpec = tween(
                        durationMillis = 220,
                        delayMillis = 90
                    )
                ) with fadeOut(animationSpec = tween(durationMillis = 90))

                target.enterTransition with target.exitTransition
            }
        ) {
            currentScreen?.Content()
        }
    }
}
