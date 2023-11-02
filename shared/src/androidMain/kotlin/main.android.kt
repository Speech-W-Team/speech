import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import wtf.speech.compass.core.RouteManager
import wtf.speech.features.home.ui.HomeScreen
import wtf.speech.features.home.ui.HomeViewModel
import wtf.speech.vault.shared.App

@Composable
fun MainView(routeManager: RouteManager) = App(routeManager)

/**
 * A function for displaying the preview version of the screen with cryptocurrencies.
 */
@Composable
@Preview
fun CryptoScreenPreview() {
    HomeScreen(HomeViewModel())
}
