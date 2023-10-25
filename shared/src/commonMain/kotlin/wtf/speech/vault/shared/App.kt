@file:OptIn(ExperimentalAnimationApi::class)

package wtf.speech.vault.shared

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.NavigationHost
import wtf.speech.compass.core.RouteManager
import wtf.speech.core.design.themes.SpeechTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(routeManager: RouteManager) {
    CompositionLocalProvider(LocalRouteManager provides routeManager) {
        SpeechTheme {
            Scaffold {
                NavigationHost(routeManager)
            }
        }
    }
}
