package wtf.speech.vault.shared

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import wtf.speech.compass.core.BaseDeepLinkScreenBuilder
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.NavigationHost
import wtf.speech.compass.core.Route
import wtf.speech.compass.core.RouteManagerImpl
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.design.themes.SpeechTheme

class HomeScreen : Screen() {
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        Text("Home Screen Graph 1")
    }

    companion object : ScreenBuilder {
        const val ID = "HomeScreen"
        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return HomeScreen()
        }
    }
}

class SettingsScreen : Screen() {
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        Text("Settings Screen Graph 1")
    }

    companion object : ScreenBuilder {
        const val ID = "SettingsScreen"
        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return SettingsScreen()
        }
    }
}

class DeepLinkScreen(private val params: Map<String, String>?, private val extra: Extra?) : Screen() {

    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val id = params?.get("articleId") ?: ""
        Text("id: $id")
    }

    companion object Builder: BaseDeepLinkScreenBuilder() {
        const val ID = "DeepLinkScreen"
        override val deepLinkPattern: String
            get() = "app://article/{articleId}"

        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return DeepLinkScreen(params, extra)
        }
    }
}

object Home2Screen : Screen() {
    const val ID = "HomeScreen"
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        Text("Home Screen Graph 2")
    }
}

object Settings2Screen : Screen() {
    const val ID = "SettingsScreen"
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        Text("Settings Screen Graph 2")
    }
}

// Define sample navigation graphs
val mainGraph = NavigationGraph(
    id = "main",
    paths = mapOf(
        "HomeScreen" to setOf("SettingsScreen", "DeepLinkScreen"),
        "SettingsScreen" to setOf("HomeScreen")
    ),
    initialScreen = HomeScreen
).apply {
    register(Route(HomeScreen.ID, HomeScreen))
    register(Route(SettingsScreen.ID, SettingsScreen))
    register(Route(DeepLinkScreen.ID, DeepLinkScreen.Builder))
}

//val settingsGraph = NavigationGraph(
//    id = "settings",
//    paths = mapOf(
//        "SettingsScreen" to setOf("HomeScreen"),
//        "HomeScreen" to setOf("SettingsScreen")
//    ),
//    initialScreen = Settings2Screen
//).apply {
//    register(Route(Settings2Screen))
//    register(Route(Home2Screen))
//}

// Usage
val routeManager = RouteManagerImpl(mainGraph).apply {
    registerGraph(mainGraph)
//    registerGraph(settingsGraph)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    SpeechTheme {
        Scaffold {
            App(routeManager)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App(routeManager: RouteManagerImpl) {
    Column {
        // Navigation buttons
        Button(onClick = {
            routeManager.navigateTo("HomeScreen")
        }) {
            Text("Go to Home")
        }

        Button(onClick = {
            routeManager.navigateTo("SettingsScreen")
        }) {
            Text("Go to Settings")
        }

        Button(onClick = {
            routeManager.navigateBack()
        }) {
            Text("Go Back")
        }

        Button(onClick = {
            routeManager.switchToGraph("settings")
        }) {
            Text("Switch to Settings Graph")
        }

        Button(onClick = {
            routeManager.switchToGraph("main")
        }) {
            Text("Switch to Main Graph")
        }

        Button(onClick = {
            routeManager.handleDeepLink("app://article/12345")
        }) {
            Text("Go to deepLink")
        }

        Button(onClick = {
            routeManager.closeActiveGraph()
        }) {
            Text("Close Active Graph")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Use NavigationHost to handle navigation
        NavigationHost(routeManager)
    }
}
