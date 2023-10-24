@file:OptIn(ExperimentalAnimationApi::class)

package wtf.speech.vault.shared

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.NavigationHost
import wtf.speech.compass.core.rememberRouteManager
import wtf.speech.core.design.themes.SpeechTheme
import wtf.speech.feature.passcode.ui.PasscodeGraphs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    CompositionLocalProvider(LocalRouteManager provides rememberRouteManager(PasscodeGraphs.createPasscodeGraph)) {
        val routeManager = LocalRouteManager.current
        SpeechTheme {
            Scaffold {
                NavigationHost(routeManager)
            }
        }
    }
}
