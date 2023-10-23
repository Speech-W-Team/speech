
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.NavigationHost
import wtf.speech.compass.core.Route
import wtf.speech.compass.core.RouteManagerImpl
import wtf.speech.compass.core.Screen

object HomeScreen : Screen {
    const val ID = "HomeScreen"
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        Text("Home Screen Graph 1")
    }
}

object SettingsScreen : Screen {
    const val ID = "SettingsScreen"
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        Text("Settings Screen Graph 1")
    }

}

class NavigationTest {
    // Define sample navigation graphs
    val mainGraph = NavigationGraph(
        id = "main",
        paths = mapOf(
            "HomeScreen" to setOf("SettingsScreen"),
            "SettingsScreen" to setOf("HomeScreen")
        ),
        initialScreen = HomeScreen
    ).apply {
        register(Route(HomeScreen))
        register(Route(SettingsScreen))
    }

    val settingsGraph = NavigationGraph(
        id = "settings",
        paths = mapOf(
            "SettingsScreen" to setOf("HomeScreen"),
            "HomeScreen" to setOf("SettingsScreen")
        ),
        initialScreen = SettingsScreen
    ).apply {
        register(Route(SettingsScreen))
        register(Route(HomeScreen))
    }

    // Usage
    val routeManager = RouteManagerImpl(mainGraph).apply {
        registerGraph(mainGraph)
        registerGraph(settingsGraph)
    }


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSwitchToSettingsGraph() {
        composeTestRule.setContent {
            App(routeManager)
        }

        // Check that the HomeScreen is displayed
        composeTestRule.onNodeWithText("Go to Home").assertIsDisplayed()

        // Click on "Switch to Settings Graph" button
        composeTestRule.onNodeWithText("Switch to Settings Graph").performClick()

        // Check that the SettingsScreen is displayed
        composeTestRule.onNodeWithText("Go to Settings").assertIsDisplayed()
    }

    @Test
    fun testNavigateBack() {
        composeTestRule.setContent {
            App(routeManager)
        }

        // Navigate to SettingsScreen
        routeManager.navigateTo("SettingsScreen")

        // Click on "Go Back" button
        composeTestRule.onNodeWithText("Go Back").performClick()

        // Check that the HomeScreen is displayed again
        composeTestRule.onNodeWithText("Go to Home").assertIsDisplayed()
    }
}

@Composable
fun App(routeManager: RouteManagerImpl) {
    Column {
        // Navigation buttons
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
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
                routeManager.closeActiveGraph()
            }) {
                Text("Close Active Graph")
            }
        }

        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

        // Use NavigationHost to handle navigation
        NavigationHost(routeManager)
    }
}
